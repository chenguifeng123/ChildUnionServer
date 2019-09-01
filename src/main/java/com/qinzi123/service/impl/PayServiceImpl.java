package com.qinzi123.service.impl;

import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.qinzi123.dao.CampaignDao;
import com.qinzi123.dto.ScoreType;
import com.qinzi123.exception.GlobalProcessException;
import com.qinzi123.service.PayService;
import com.qinzi123.service.ScoreService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenguifeng on 2019/3/18.
 */
@Service
public class PayServiceImpl extends AbstractWeixinService implements PayService {

	private static final String TRADE_TYPE = "JSAPI";

	private static final String KEY = "555006250b4c09247ec02edce69f6a2d";

	private static final int PERCENT = 100;

	private Logger log = LoggerFactory.getLogger(PayServiceImpl.class);

	@Autowired
	CampaignDao campaignDao;

	@Autowired
	ScoreService scoreService;

	private RestTemplate localRestTemplate;

	public RestTemplate getLocalRestTemplate() {
		if(localRestTemplate == null){
			localRestTemplate = new RestTemplate();
			localRestTemplate.getMessageConverters().add(0,
					new StringHttpMessageConverter(Charset.forName("UTF-8")));
		}
		return localRestTemplate;
	}

	private String convertFee(String fee){
		BigDecimal bigDecimal = new BigDecimal(fee);
		BigDecimal result = bigDecimal.multiply(new BigDecimal(PERCENT)).setScale(0);
		return String.valueOf(result.toString());
	}

	/**
	 * 检查支付的用户是否存在
	 * @param map
	 */
	private Map checkUser(Map map){
		String card = map.get("card").toString();
		Map cardMap = cardDao.getCardInfoById(card);
		if(cardMap == null || cardMap.size() == 0 || cardMap.get("openid") == null)
			throw new GlobalProcessException("用户不存在");
		String openid = cardMap.get("openid").toString();
		map.put("openid", openid);
		return map;
	}

	/**
	 * 构造支付信息
	 * @param map 前端传入的订单相关信息
	 * @return 构建好的Map结构体
	 * @throws Exception
	 */
	Map generatePay(Map map) throws Exception{
		String nonceStr= WXPayUtil.generateNonceStr();
		Map<String, String> packageParams = new HashMap<String ,String>();
		packageParams.put("appid", getAppId());
		packageParams.put("attach", map.get("id").toString());
		packageParams.put("body", map.get("body").toString());
		packageParams.put("mch_id", getMchId());
		packageParams.put("nonce_str", nonceStr);
		packageParams.put("notify_url", NOTIFY_URL);//支付成功后的回调地址
		packageParams.put("openid", map.get("openid").toString());//支付方式
		packageParams.put("out_trade_no", map.get("order").toString());//商户订单号
		packageParams.put("sign_type", WXPayConstants.MD5);
		packageParams.put("spbill_create_ip",getLocalIp());
		packageParams.put("total_fee", convertFee(map.get("total").toString()));//支付金额，这边需要转成字符串类型，否则后面的签名会失败
		packageParams.put("trade_type", TRADE_TYPE);//支付方式
		packageParams.put("sign", WXPayUtil.generateSignature(packageParams, KEY));
		return packageParams;
	}

	/**
	 * 构造签名的结构体，返回给前端
	 * @param nonceStr 随机字符串
	 * @param prepayId 预支付Id
	 * @return 结构体
	 * @throws Exception
	 */
	Map generateSign(String nonceStr, String prepayId)throws Exception{
		Map returnClientMap = new HashMap<>();

		returnClientMap.put("appId", getAppId());
		returnClientMap.put("timeStamp",String.valueOf(System.currentTimeMillis()/1000));
		returnClientMap.put("nonceStr", nonceStr);
		returnClientMap.put("package", String.format("prepay_id=%s", prepayId));
		returnClientMap.put("signType", WXPayConstants.MD5);
		returnClientMap.put("paySign", WXPayUtil.generateSignature(returnClientMap, KEY));

		return returnClientMap;
	}

	/**
	 * 解析支付返回结构体
	 * @param result
	 * @return
	 */
	String parseResult4Id(String result) throws Exception{
		//Map<String, String> resultMap = CommonUtil.parseXml(result);
		Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
		log.info(String.format("解析后的结果%s",result.toString()));

		String return_code = resultMap.get("return_code");
		String prepayId = "-1";
		if(StringUtils.isNotBlank(return_code) && return_code.equals("SUCCESS")) {
			String return_msg = resultMap.get("return_msg");
			if(StringUtils.isBlank(return_msg) || return_msg.equals("OK")) {
				prepayId = resultMap.get("prepay_id").toString();
			}
		}
		if(prepayId.equals("-1")) throw new GlobalProcessException("预支付流程失败");
		return prepayId;
	}

	/**
	 * 预支付
	 * @param map
	 * @return
	 */
	Map postPrepay(Map map) throws Exception{
		Map params = generatePay(map);
		String xml = WXPayUtil.mapToXml(params);
		log.info(xml);
		String resultString = getLocalRestTemplate().postForObject(PREPAY_URL, xml, String.class);
		log.info(String.format("预订单的返回结果：%s",resultString));
		return generateSign(params.get("nonce_str").toString(), parseResult4Id(resultString));
	}

	@Override
	@Transactional
	public Map payScore(Map map) {
		log.info(map.toString());
		checkUser(map);
		int card = Integer.parseInt(map.get("card").toString());
		int payment = Integer.parseInt(map.get("payment").toString());
		Map scoreMap = new HashMap<>();
		scoreMap.put("orderId", map.get("id").toString());
		scoreMap.put("orderNo", map.get("order").toString());
		scoreMap.put("payment", payment);
		scoreMap.put("message", map.get("body").toString());
		int pay = campaignDao.addPaymentScore(scoreMap);
		if(pay >= 0){
			log.info("支付成功,更新状态");
			campaignDao.updateOrder(map);
			log.info("支付成功,更新积分");
			scoreService.minusScore(card, payment);
		}
		return map;
	}

	/**
	 * 预支付接口
	 * @param map
	 * @return
	 */
	@Override
	public Map prepay(Map map) {
		try{
			log.info("开始预支付...");
			return postPrepay(checkUser(map));
		}catch (Exception e){
			throw new GlobalProcessException("预支付流程失败", e);
		}
	}

	/**
	 * 回调接口
	 * @param map
	 * @param message
	 * @return
	 */
	public Map payBack(Map map, String message) {
		log.info(map.toString());
		Map pay = new HashMap<>();
		Map order = new HashMap<>();
		String id = map.get("attach").toString();
		pay.put("orderId", id);
		pay.put("orderNo", map.get("out_trade_no").toString());
		pay.put("payment", map.get("total_fee").toString());
		pay.put("message", message);
		campaignDao.addPayment(pay);

		order.put("id", id);
		String result_code = map.get("result_code").toString();
		log.info(String.format("result_code=%s", result_code));
		if(StringUtils.isNotBlank(result_code) && result_code.equals("SUCCESS")){
			log.info("支付成功,更新状态");
			campaignDao.updateOrder(order);
			log.info("支付成功,更新积分");
			Map cardInfo = cardDao.getCardInfoByOpenId(map.get("openid").toString());
			int card = Integer.parseInt(cardInfo.get("id").toString());
			int payment = Integer.parseInt(map.get("total_fee").toString());
			scoreService.addScore(card, ScoreType.Pay, payment);
		}
		return pay;
	}

	@Override
	public Map payBack(String result) {
		try {
			return payBack(WXPayUtil.xmlToMap(result), result);
		}catch (Exception e){
			throw new GlobalProcessException("解析结果失败", e.getMessage());
		}
	}
}

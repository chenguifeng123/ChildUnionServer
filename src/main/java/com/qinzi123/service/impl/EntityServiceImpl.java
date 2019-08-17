package com.qinzi123.service.impl;

import com.qinzi123.dao.CardDao;
import com.qinzi123.dao.EntityDao;
import com.qinzi123.dao.TableConfigDao;
import com.qinzi123.dto.*;
import com.qinzi123.service.EntityService;
import com.qinzi123.service.PushService;
import com.qinzi123.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class EntityServiceImpl implements EntityService {

	@Autowired
	public EntityDao entityDao;

	@Autowired
	public TableConfigDao tableConfigDao;

	@Autowired
	PushService pushService;

	@Autowired
	CardDao cardDao;

	private HashMap<String, List<String>> tableKeyMap = new HashMap<String, List<String>>();
	private HashMap<String, HashMap<String, Column>> tableColumnMap = new HashMap<String, HashMap<String, Column>>();

	// 获取表的主键,作为修改和删除的依据
	private List<String> getKeyByTable(String tableName){
		if(tableKeyMap.size() == 0){
			List<TableConfig> list = tableConfigDao.findTableConfig();
			for(TableConfig tableConfig : list){
				List<String> keyList = Arrays.asList(tableConfig.getKeyList().split(","));
				tableKeyMap.put(tableConfig.getTableName(),  keyList);
			}
		}
		return tableKeyMap.get(tableName);
	}

	private HashMap<String, Column> getColumnByTable(String tableName){
		if(tableColumnMap.size() == 0){
			List<HashMap> list = tableConfigDao.findTableColumn();
			for(HashMap map : list){
				String table = map.get("table_name").toString();
				String columnName = map.get("column_name").toString();
				HashMap<String, Column> tableMap = tableColumnMap.get(table) == null ? new HashMap<String, Column>() : tableColumnMap.get(table);
				tableMap.put(columnName, new Column(columnName, map.get("table_name").toString()));
				tableColumnMap.put(table, tableMap);
			}
		}
		return tableColumnMap.get(tableName);
	}

	interface Loop{
		void run(String key, String value);
	}

	// 迭代前端传入的数据
	private void iterator(Map<String, Object> map, Loop loop){
		Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
		while(iterator.hasNext()){
			Map.Entry<String, Object> entry = iterator.next();
			loop.run(entry.getKey(), entry.getValue().toString());
		}
	}

	// 插入数据
	public int addService(String tableName, Map<String, Object> map){
		List<String> keyColumns = new LinkedList<String>();
		List<String> valueColumns = new LinkedList<String>();
		List<String> keys = getKeyByTable(tableName);
		iterator(map, new Loop() {
			@Override
			public void run(String key, String value) {
				if(keys.contains(key)) return; // 目前所有主键都是自动生成,这里屏蔽
				keyColumns.add(key);
				valueColumns.add(Utils.fillColumn(value));
			}
		});
		return entityDao.addEntity(tableName, Utils.join(keyColumns, ","), Utils.join(valueColumns, ","));
	}

	private void loadKeyColumns(String tableName, Map<String, Object> map, List<String> keyColumns, List<String> keys, List<String> updateColumns){
		HashMap<String, Column> columnMap = getColumnByTable(tableName);
		iterator(map, new Loop() {
			@Override
			public void run(String key, String value) {
				Column column = columnMap.get(key);
				// 类型判断和处理,临时写一下,以后再考虑系统处理
				if(column == null || value == null) return;
				if(column.getDataType().equals("int") && value.length() == 0) return;
				String oneSetting = String.format(Utils.EQUAL_SQL, key, Utils.fillColumn(value));
				if(keys.contains(key)){
					keyColumns.add(oneSetting);
				}else{
					if (updateColumns != null) updateColumns.add(oneSetting);
				}
			}
		});
	}

	public void callMessageUser(String tableName, Map<String, Object> map){
		int verify = Integer.parseInt(map.get("verify").toString());
		if(verify == 1 || verify == 2){
			String message = String.format("审批%s通过", verify == 2 ? "不" : "");
			CardInfo cardInfo = cardDao.getCardInfoBeanById(Integer.parseInt(map.get("card_id").toString()));
			if(tableName.equalsIgnoreCase("card_message")) {
				CardMessage cardMessage = ServiceHelper.convertMap2CardMessage(map, cardInfo,
						message);
				pushService.pushMessage2OneUser(cardMessage);
			}else{
				CardMessageReply cardMessageReply = ServiceHelper.convertMap2CardMessageReply(
						map,message);
				pushService.pushMessageReply2OneUser(cardMessageReply);
			}
		}
	}

	// 修改数据
	public int updateService(String tableName, Map<String, Object> map){
		List<String> updateColumns = new LinkedList<String>();
		List<String> keyColumns = new LinkedList<String>();
		loadKeyColumns(tableName, map, keyColumns, getKeyByTable(tableName), updateColumns);
		if(tableName.equalsIgnoreCase("card_message") || tableName.equalsIgnoreCase("card_message_reply"))
			callMessageUser(tableName, map);
		return entityDao.updateEntity(tableName, Utils.join(updateColumns, ","), Utils.join(keyColumns, Utils.AND));
	}

	// 删除数据
	public int deleteService(String tableName, List<Map<String, Object>> list){
		List<String> allRecordKeys = new LinkedList<String>();
		for(Map<String, Object> map : list) {
			List<String> keyColumns = new LinkedList<String>();
			loadKeyColumns(tableName, map, keyColumns, getKeyByTable(tableName), null);
			allRecordKeys.add(String.format("(%s)", Utils.join(keyColumns, Utils.AND)));
		}
		return entityDao.deleteEntity(tableName, Utils.join(allRecordKeys, Utils.OR));
	}

	// 根据key查找数据
	public List<LinkedHashMap> showService(String tableName, Map<String, Object> map){
		String queryTableName = tableName;
		List<String> keyColumns = new LinkedList<String>();
		loadKeyColumns(queryTableName, map, keyColumns, getKeyByTable(queryTableName), null);
		return entityDao.findEntityByKey(queryTableName, Utils.join(keyColumns, Utils.AND));
	}

	// 查询所有数据，目前分页在客户端做，将来可以考虑服务端做
	public List<LinkedHashMap> showAllService(String tableName){
		return entityDao.findEntitys(tableName);
	}

	@Override
	public List<LinkedHashMap> showSpecialService(Map<String, Object> map) {
		String tableName = map.get("model").toString();
		String key = map.get("key").toString();
		String value =  map.get("value").toString();
		String selectList = Utils.join(new String[]{key, value}, ",");
		return entityDao.findSpecialEntitys(selectList, tableName);
	}
}

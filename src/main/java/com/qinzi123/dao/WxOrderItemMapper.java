package com.qinzi123.dao;

import com.qinzi123.po.WxOrderItem;
import com.qinzi123.po.WxOrderItemExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface WxOrderItemMapper {
    int countByExample(WxOrderItemExample example);

    int deleteByExample(WxOrderItemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WxOrderItem record);

    int insertSelective(WxOrderItem record);

    List<WxOrderItem> selectByExampleWithRowbounds(WxOrderItemExample example, RowBounds rowBounds);

    List<WxOrderItem> selectByExample(WxOrderItemExample example);

    WxOrderItem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WxOrderItem record, @Param("example") WxOrderItemExample example);

    int updateByExample(@Param("record") WxOrderItem record, @Param("example") WxOrderItemExample example);

    int updateByPrimaryKeySelective(WxOrderItem record);

    int updateByPrimaryKey(WxOrderItem record);
}
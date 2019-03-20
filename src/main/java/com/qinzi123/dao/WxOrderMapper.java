package com.qinzi123.dao;

import com.qinzi123.po.WxOrder;
import com.qinzi123.po.WxOrderExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface WxOrderMapper {
    int countByExample(WxOrderExample example);

    int deleteByExample(WxOrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WxOrder record);

    int insertSelective(WxOrder record);

    List<WxOrder> selectByExampleWithRowbounds(WxOrderExample example, RowBounds rowBounds);

    List<WxOrder> selectByExample(WxOrderExample example);

    WxOrder selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WxOrder record, @Param("example") WxOrderExample example);

    int updateByExample(@Param("record") WxOrder record, @Param("example") WxOrderExample example);

    int updateByPrimaryKeySelective(WxOrder record);

    int updateByPrimaryKey(WxOrder record);
}
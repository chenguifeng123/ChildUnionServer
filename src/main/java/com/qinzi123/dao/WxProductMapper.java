package com.qinzi123.dao;

import com.qinzi123.po.WxProduct;
import com.qinzi123.po.WxProductExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface WxProductMapper {
    int countByExample(WxProductExample example);

    int deleteByExample(WxProductExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WxProduct record);

    int insertSelective(WxProduct record);

    List<WxProduct> selectByExampleWithRowbounds(WxProductExample example, RowBounds rowBounds);

    List<WxProduct> selectByExample(WxProductExample example);

    WxProduct selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WxProduct record, @Param("example") WxProductExample example);

    int updateByExample(@Param("record") WxProduct record, @Param("example") WxProductExample example);

    int updateByPrimaryKeySelective(WxProduct record);

    int updateByPrimaryKey(WxProduct record);
}
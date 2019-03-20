package com.qinzi123.dao;

import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by chenguifeng on 2019/3/11.
 */
public interface EntityDao {

	public int addEntity(@Param("tableName")String tableName, @Param("tableColumns") String tableColumns, @Param("tableValues") String tableValues);
	public int updateEntity(@Param("tableName")String tableName, @Param("updateColumns") String updateColumns, @Param("keyColumns") String keyColumns);
	public int deleteEntity(@Param("tableName")String tableName, @Param("keyColumns") String keyColumns);
	public List<LinkedHashMap> findEntityByKey(@Param("tableName")String tableName, @Param("keyColumns") String keyColumns);
	public List<LinkedHashMap> findEntitys(@Param("tableName")String tableName);

}

package com.qinzi123.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*
    服务端主要服务处理类
    主要负责数据库的 增删改查操作
 */

public interface EntityService {
	
    public int addService(String tableName, Map<String, Object> map);
    public int updateService(String tableName, Map<String, Object> map);
    public int deleteService(String tableName, List<Map<String, Object>> list);
    public List<LinkedHashMap> showService(String tableName, Map<String, Object> map);
    public List<LinkedHashMap> showAllService(String tableName);
    public List<LinkedHashMap> showSpecialService(Map<String, Object> map);
    List<LinkedHashMap> showSaaSService(String tableName, String condition);

}



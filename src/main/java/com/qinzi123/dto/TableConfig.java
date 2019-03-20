package com.qinzi123.dto;

/**
 * 表关键字模型
 */
public class TableConfig {

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getKeyList() {
        return keyList;
    }

    public void setKeyList(String keyList) {
        this.keyList = keyList;
    }

    private String tableName;
    private String keyList;
}

package com.qinzi123.dao;

import com.qinzi123.dto.TableConfig;

import java.util.HashMap;
import java.util.List;

/**
 * 处理表关键字映射的接口
 */
public interface TableConfigDao {
    public List<TableConfig> findTableConfig();
    public List<HashMap> findTableColumn();
}

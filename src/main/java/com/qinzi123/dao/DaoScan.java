package com.qinzi123.dao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;

@Component
@MapperScan(basePackages = "com.qinzi123.dao")
public class DaoScan {
}

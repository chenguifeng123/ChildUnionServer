package com.qinzi123;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class QinziApplication {
    public static void main(String[] args){
        SpringApplication.run(QinziApplication.class, args);
    }
}

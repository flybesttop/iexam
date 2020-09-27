package com.sot.iexam;

import lombok.Setter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Kimbobo
 */
@SpringBootApplication
@MapperScan("com.sot.iexam.DAO.mybatis.mapper")
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class IexamApplication {

    public static void main(String[] args) {
        SpringApplication.run(IexamApplication.class, args);
    }

}

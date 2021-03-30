package com.cpt202.cw1.findu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cpt202.cw1.findu.mapper")
public class FinduApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinduApplication.class, args);
    }

}

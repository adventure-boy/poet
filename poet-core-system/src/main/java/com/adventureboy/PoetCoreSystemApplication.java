package com.adventureboy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("")
public class PoetCoreSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(PoetCoreSystemApplication.class, args);
    }
}

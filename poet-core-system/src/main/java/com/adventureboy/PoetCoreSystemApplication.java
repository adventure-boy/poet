package com.adventureboy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan("com.adventureboy.system.mapper")
public class PoetCoreSystemApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(PoetCoreSystemApplication.class, args);
        System.out.println("he");
    }
}

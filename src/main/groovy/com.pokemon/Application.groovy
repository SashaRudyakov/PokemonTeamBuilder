package com.pokemon

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
@SpringBootApplication(exclude = DataSourceAutoConfiguration)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
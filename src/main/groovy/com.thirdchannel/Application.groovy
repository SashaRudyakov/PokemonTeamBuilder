package com.thirdchannel

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
//import org.springframework.transaction.annotation.EnableTransactionManagement
//import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity

//@EnableTransactionManagement
//@EnableWebMvcSecurity
@SpringBootApplication(exclude = DataSourceAutoConfiguration)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
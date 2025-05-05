package com.backend.springcloud.mcsv.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
// @EnableDiscoveryClient
public class UsersMicroserviciosSpringCloudApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersMicroserviciosSpringCloudApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(UsersMicroserviciosSpringCloudApplication.class, args);

        LOGGER.info("Users Microservice is running...Proyecto Dev  ¡¡¡¡¡¡¡¡¡¡INITIALIZED!!!!!!!!!!!...");
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}

package com.amdox.taskmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AmdoxTaskManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmdoxTaskManagementApplication.class, args);
    }

}

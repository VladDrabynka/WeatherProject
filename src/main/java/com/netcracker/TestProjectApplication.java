package com.netcracker;

import com.netcracker.configuration.ApplicationConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(new Object[]{TestProjectApplication.class, ApplicationConfiguration.class}, args);
    }
}

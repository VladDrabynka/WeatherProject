package com.netcracker;

import com.netcracker.configuration.ApplicationConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TestProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(new Object[]{TestProjectApplication.class, ApplicationConfiguration.class}, args);
    }
}

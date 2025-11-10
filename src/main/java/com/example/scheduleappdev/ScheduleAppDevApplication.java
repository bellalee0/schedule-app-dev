package com.example.scheduleappdev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ScheduleAppDevApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleAppDevApplication.class, args);
    }

}

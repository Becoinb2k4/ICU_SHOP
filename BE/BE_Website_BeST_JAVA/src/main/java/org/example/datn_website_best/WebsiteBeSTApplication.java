package org.example.datn_website_best;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WebsiteBeSTApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebsiteBeSTApplication.class, args);
    }

}

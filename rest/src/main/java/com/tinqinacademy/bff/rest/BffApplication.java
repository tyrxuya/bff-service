package com.tinqinacademy.bff.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.tinqinacademy.authentication"})
@EnableJpaRepositories(basePackages = {"com.tinqinacademy.authentication.persistence.repositories"})
@EntityScan(basePackages = {"com.tinqinacademy.authentication.persistence.entities"})
public class BffApplication {

    public static void main(String[] args) {
        SpringApplication.run(BffApplication.class, args);
    }

}

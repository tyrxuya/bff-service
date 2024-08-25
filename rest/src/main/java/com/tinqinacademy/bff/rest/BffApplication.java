package com.tinqinacademy.bff.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.tinqinacademy.bff"})
//@EnableJpaRepositories(basePackages = {"com.tinqinacademy.bff.persistence.repositories"})
//@EntityScan(basePackages = {"com.tinqinacademy.bff.persistence.entities"})
@EnableFeignClients(basePackages = {"com.tinqinacademy.bff"})
public class BffApplication {

    public static void main(String[] args) {
        SpringApplication.run(BffApplication.class, args);
    }

}

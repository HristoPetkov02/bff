package com.tinqinacademy.bff.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.tinqinacademy.bff")
@EnableFeignClients(basePackages = "com.tinqinacademy.bff.domain.feignclients")
public class BffApplication {
	public static void main(String[] args) {
		SpringApplication.run(BffApplication.class, args);
	}

}

package com.minerworks.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@SpringBootApplication

public class MinerWorksWebApp {

	public static void main(String[] args) {
		SpringApplication.run(MinerWorksWebApp.class, args);
	}

}

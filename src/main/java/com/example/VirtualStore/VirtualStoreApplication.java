package com.example.VirtualStore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class VirtualStoreApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(VirtualStoreApplication.class, args);
	}

}

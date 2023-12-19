package com.mtech.assgnproj;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class ProjSpringBootApplication extends SpringBootServletInitializer implements CommandLineRunner{
	public static void main(String[] args) {
        SpringApplication.run(ProjSpringBootApplication.class, args);
    }
	
	public void run(String... args) {
		System.out.println("Java service has started.");
	}

}

package com.myproject.sumup.notification_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableJpaAuditing
@SpringBootApplication
public class SumupNotificationSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SumupNotificationSystemApplication.class, args);
	}
	
}

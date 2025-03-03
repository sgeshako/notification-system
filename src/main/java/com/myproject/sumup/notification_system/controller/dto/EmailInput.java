package com.myproject.sumup.notification_system.controller.dto;

import lombok.Data;

@Data
public class EmailInput {
	private String subject;
	private String body;
	private String recipient;

}

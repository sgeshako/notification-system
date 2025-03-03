package com.myproject.sumup.notification_system.controller.dto;

import lombok.Data;

@Data
public class SmsInput {
	private String recipient;
	private String body;
}

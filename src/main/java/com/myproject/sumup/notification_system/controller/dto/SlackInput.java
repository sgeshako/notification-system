package com.myproject.sumup.notification_system.controller.dto;

import lombok.Data;

@Data
public class SlackInput {
	private String channel;
	private String message;
}

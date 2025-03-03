package com.myproject.sumup.notification_system.controller.validator;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class InputValidator {

	private InputValidator() {
	}
	
	private static final Pattern EMAIL = Pattern.compile("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}");
	private static final Pattern PHONE_NUMBER = Pattern.compile("\\+\\d{12}");
	private static final Pattern SLACK_CHANNEL = Pattern.compile("[0-9A-Z]{11}");
	
	
	public static boolean validateEmail(String email) {
		return StringUtils.isNotBlank(email) && EMAIL.matcher(email).matches();
	}
	
	public static boolean validatePhoneNumber(String phone) {
		return StringUtils.isNotBlank(phone) && PHONE_NUMBER.matcher(phone).matches();
	}
	
	public static boolean validateSlackChannel(String channel) {
		return StringUtils.isNotBlank(channel) && SLACK_CHANNEL.matcher(channel).matches();
	}

}

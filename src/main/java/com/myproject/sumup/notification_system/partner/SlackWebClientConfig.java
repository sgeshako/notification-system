package com.myproject.sumup.notification_system.partner;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.slack.api.Slack;

@Configuration
public class SlackWebClientConfig {

	@Bean
	public Slack slackWebClient() {
		Slack slack = Slack.getInstance();
		return slack;
	}
}

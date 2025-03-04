package com.myproject.sumup.notification_system.mq;

import java.text.MessageFormat;
import java.util.function.Function;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.myproject.sumup.notification_system.domain.MessageChannel;

@Configuration
public class RabbitMqConfig {
	
	public static final String DIRECT_EXCHANGE_NAME = "my_direct_exchange";

	public static final String PROCESSING_EMAIL_QUEUE = "processing_email_queue";
	public static final String PROCESSING_SMS_QUEUE = "processing_sms_queue";
	public static final String PROCESSING_SLACK_QUEUE = "processing_slack_queue";

	public static final Function<MessageChannel, String> RESOLVE_ROUTING_KEY = 
			(channel) -> MessageFormat.format("process.{0}", channel);
	
	@Bean
	Queue processingEmailQueue() {
		return new Queue(PROCESSING_EMAIL_QUEUE, true);
	}
	
	@Bean
	Queue processingSmsQueue() {
		return new Queue(PROCESSING_SMS_QUEUE, true);
	}
	
	@Bean
	Queue processingSlackQueue() {
		return new Queue(PROCESSING_SLACK_QUEUE, true);
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange(DIRECT_EXCHANGE_NAME);
	}

	@Bean
	Binding emailBinding(Queue processingEmailQueue, DirectExchange exchange) {
		return BindingBuilder
				.bind(processingEmailQueue)
				.to(exchange)
				.with(RESOLVE_ROUTING_KEY.apply(MessageChannel.EMAIL));
	}
	
	@Bean
	Binding smsBinding(Queue processingSmsQueue, DirectExchange exchange) {
		return BindingBuilder
				.bind(processingSmsQueue)
				.to(exchange)
				.with(RESOLVE_ROUTING_KEY.apply(MessageChannel.SMS));
	}
	
	@Bean
	Binding slackBinding(Queue processingSlackQueue, DirectExchange exchange) {
		return BindingBuilder
				.bind(processingSlackQueue)
				.to(exchange)
				.with(RESOLVE_ROUTING_KEY.apply(MessageChannel.SLACK));
	}
	
	
	/**
	 * Convert queue messages to/from JSON
	 */
	@Bean
	public MessageConverter jsonMessageConverter() {
	    return new Jackson2JsonMessageConverter();
	}
	
}

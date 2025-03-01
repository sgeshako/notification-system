package com.myproject.sumup.notification_system.mq;

import static com.myproject.sumup.notification_system.mq.RabbitMqConfig.DIRECT_EXCHANGE_NAME;
import static com.myproject.sumup.notification_system.mq.RabbitMqConfig.RESOLVE_ROUTING_KEY;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.myproject.sumup.notification_system.domain.MessageChannel;

@Component
public class MessagePublisher {
	
	private static final Logger LOG = LoggerFactory.getLogger(MessagePublisher.class);

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Async
	public void sendAsyncMessage(UUID messageId, MessageChannel channel) {
		LOG.info("Publishing message {} for channel {}.", messageId, channel);
		rabbitTemplate.convertAndSend(
				DIRECT_EXCHANGE_NAME, 
				RESOLVE_ROUTING_KEY.apply(channel), 
				new MessageId(messageId));
	}
}

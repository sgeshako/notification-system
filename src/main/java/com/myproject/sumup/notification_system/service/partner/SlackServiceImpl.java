package com.myproject.sumup.notification_system.service.partner;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myproject.sumup.notification_system.domain.data.SlackMsgData;
import com.myproject.sumup.notification_system.repository.SlackDataRepository;
import com.myproject.sumup.notification_system.service.ProcessingException;
import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;

@Profile({ "prod", "local" })
@Transactional
@Service
public class SlackServiceImpl implements SlackService {
	
	private static final Logger LOG = LoggerFactory.getLogger(SlackServiceImpl.class);
	
	@Value("${slack.bot.token}")
	private String botToken;

	@Autowired
	private Slack slackWebClient;
	
	@Autowired
	private SlackDataRepository slackDataRepo;
	
	@Override
	public void sendSlackMessage(UUID messageId) throws ProcessingException {
		SlackMsgData slackMsgData = slackDataRepo.findById(messageId)
				.orElseThrow(() -> new ProcessingException("No slack data found for messageId " + messageId));
		
		try {
			ChatPostMessageResponse response = 
					slackWebClient.methods(botToken)
								  .chatPostMessage(req -> req.channel(slackMsgData.getChannel())
															 .text(slackMsgData.getMessage()));
			if (!response.isOk()) {
				throw new ProcessingException(response.getError() != null ? response.getError() : "Response NOK");
			}
		} catch (IOException e) {
			LOG.error("Network error", e);
			throw new ProcessingException("Network error", e);
		} catch (SlackApiException e) {
			LOG.error("Bad response", e);
			throw new ProcessingException("Bad response", e);
		}
	}
}

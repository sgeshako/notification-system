package com.myproject.sumup.notification_system.service;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myproject.sumup.notification_system.domain.Processing;
import com.myproject.sumup.notification_system.domain.ProcessingStatus;
import com.myproject.sumup.notification_system.repository.ProcessingRepository;

@Transactional
@Service
public class ProcessingService {
	
	@Autowired
	private ProcessingRepository processingRepo;
	
	/**
	 * Update the status of the given Message.
	 * 
	 * @param messageId
	 * @param status
	 */
	public void updateProcessingStatus(UUID messageId, ProcessingStatus status) {
		
		updateProcessingStatus(messageId, status, null);
	}
	
	/**
	 * Update the status with details for the given Message.
	 * 
	 * @param messageId
	 * @param status
	 * @param details
	 */
	public void updateProcessingStatus(UUID messageId, ProcessingStatus status, String details) {
		
		Processing oldStatus = processingRepo.findByMessage_Id(messageId);
		processingRepo.delete(oldStatus);
		
		Processing newStatus = new Processing();
		newStatus.setMessage(oldStatus.getMessage());
		newStatus.setStatus(status);
		newStatus.setDetails(StringUtils.trimToNull(details));
		processingRepo.save(newStatus);
	}

}

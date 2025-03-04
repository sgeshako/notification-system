package com.myproject.sumup.notification_system.domain;

/**
 * Status of a Message during processing.
 */
public enum ProcessingStatus {
	ARRIVED,
	IN_PROGRESS,
	SEND_SUCCESSFUL,
	SEND_FAILURE
}

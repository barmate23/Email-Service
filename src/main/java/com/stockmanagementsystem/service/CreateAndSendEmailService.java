package com.stockmanagementsystem.service;

import com.stockmanagementsystem.entity.Email;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CreateAndSendEmailService {


	// TODO Email delivery switches According to Type of message...........->
		@Async
		String createEmail(List<Email> emails);

	//TODO this method send email for only pending tasks..........->
	Boolean generateOtp(String type, int userId, String subject, int emailId, int count, String otp);
}

package com.utils;

import org.springframework.mail.SimpleMailMessage;

import com.services.EmailService;

public class EmailHelper {

	private static EmailService emailService;

	EmailHelper(EmailService emailService) {
		EmailHelper.emailService = emailService;
	}

	public static void sendEmail(String to, String subject, String message, String from) {
		SimpleMailMessage registrationEmail = new SimpleMailMessage();
		registrationEmail.setTo(to);
		registrationEmail.setSubject(subject);
		registrationEmail.setText(message);
		registrationEmail.setFrom(from);

		emailService.sendEmail(registrationEmail);
	}

}

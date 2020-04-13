package com.techelevator.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.mail.MessagingException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.gmail.Mailer;

@RestController
@RequestMapping("/api/dailynotification")
@CrossOrigin
public class DailyNotificationController {

	@PostMapping
	public void sendDailyNotification(@RequestBody String recipientEmailAddress) {
		String message = "OK";
		
		Mailer mailer = new Mailer();
		try {
			mailer.sendDailyNotification(recipientEmailAddress);
		} catch (GeneralSecurityException e) {
			message = e.getMessage();
			e.printStackTrace();
		} catch (IOException e) {
			message = e.getMessage();
			e.printStackTrace();
		} catch (MessagingException e) {
			message = e.getMessage();
			e.printStackTrace();
		}
		
		System.out.println(message);
	}
	

}

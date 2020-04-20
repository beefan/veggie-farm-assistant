package com.techelevator.controller;

import java.io.IOException;

import java.security.GeneralSecurityException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.gmail.Mailer;
import com.techelevator.model.NotificationDAO;

@RestController
@RequestMapping("/api/dailynotification")
@CrossOrigin
public class DailyNotificationController {

	private boolean isRegisteredForEmail = false;

	@Autowired
	private NotificationDAO notificationDao;

	@PostMapping
	public void sendDailyNotification(@RequestBody String recipientEmailAddress) {
		String message = "OK";
		if (!isRegisteredForEmail) {
			Mailer mailer = new Mailer();
			try {
				mailer.sendDailyNotification(recipientEmailAddress, notificationDao.compileNotification());
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
		isRegisteredForEmail = !isRegisteredForEmail;
	}
	
	@GetMapping
	public String getDailyNotifications() {
		return notificationDao.compileNotification();
	}

	@GetMapping("/isRegistered")
	public boolean getRegisteredForEmail() {
		return isRegisteredForEmail;
	}

}

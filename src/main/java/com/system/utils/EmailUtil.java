package com.system.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {
	public static void sendCredentials(String toEmail, String name, String tempPassword, String role) {
		final String fromEmail = "kadg.kavinda2001@gmail.com";
		final String password = "iyrj soin gfyl frho";
		
		Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        
        Session session = Session.getInstance(props, new Authenticator() {
        	protected PasswordAuthentication getPasswordAuthentication() {
        		return new PasswordAuthentication(fromEmail, password);
        	}
        });
        
        try {
        	MimeMessage message = new MimeMessage(session);
        	message.setFrom(new InternetAddress(fromEmail));
        	message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        	
        	if("staff".equals(role) || "admin".equals(role)) {
        		message.setSubject("Your ABC Modern Kitchen " + role.toUpperCase() + " Account Credentials");
        		String emailContent = "<html>"
        				+ "<body>"
        				+ "<h2>Welcome to ABC Modern Kitchen!</h2>"
        				+ "<p>Dear " + name + ",</p>"
        				+ "<p>Your " + role + " account has been created successfully.</p>"
        				+ "<p><strong>Email:</strong> " + toEmail + "</p>"
        				+ "<p><strong>Temporary Password:</strong> " + tempPassword + "</p>"
        				+ "<p><strong>Please login and change your password immediately.</strong></p>"
        				+ "<br>"
        				+ "<p>Best regards,<br>ABC Modern Kitchen Team</p>"
        				+ "</body>"
        				+ "</html>";
        		
        		message.setContent(emailContent, "text/html");
        	}
        	
        	Transport.send(message);
        	System.out.println("[SUCCESS] Email sent successfully to: " + toEmail);
        	
        	
        } catch(Exception e) {
        	System.out.println("[ERROR] Failed to send email: " + e.getMessage());
            e.printStackTrace();
        }
				
	}
	
	public static void sendWelcomeEmail(String toEmail, String name, String role) {
		final String fromEmail = "kadg.kavinda2001@gmail.com";
		final String password = "iyrj soin gfyl frho";
		
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		});
		
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromEmail));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
			
			String emailContent = "<html>"
	                + "<body>"
	                + "<h2>Welcome to ABC Modern Kitchen!</h2>"
	                + "<p>Dear " + name + ",</p>"
	                + "<p>Your customer account has been created successfully.</p>"
	                + "<p>You can now login to our system using the password you chose during registration.</p>"
	                + "<br>"
	                + "<p>Best regards,<br>ABC Modern Kitchen Team</p>"
	                + "</body>"
	                + "</html>";
				
				message.setContent(emailContent, "text/html");
			
			Transport.send(message);
			System.out.println("[SUCCESS] Email sent successfully to: " + toEmail);
			
			
		} catch(Exception e) {
			System.out.println("[ERROR] Failed to send email: " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	
}

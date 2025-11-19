package com.system.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.system.model.DineInReservation;

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
	
	public static void sendNewReservationNotification(String staffEmail, DineInReservation dineInReservation) {
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
	    	message.addRecipient(Message.RecipientType.TO, new InternetAddress(staffEmail));
	    	
	    	message.setSubject("New Reservation Receive: - " + dineInReservation.getReservationCode());
	    	
	    	String emailContent = "<html>"
	    			+ "<body style='font-family: Arial, sans-sarif; line-height: 1.6; color: #333;'>"
	    			+ "<div style='max-width:600px; margin: 0 auto; padding: 20px; border: 1px solod #ddd; border-radius: 10px;'>"
	    			+ "<h2 style='color: #2c3e50; text-align: centrt;'>ABC Modern Kitchen - New Reservation</h2>"
	    			+ "<div style='background: #f8f9fa; padding: 15px; border-radius: 5px; margin: 20px 0'>"
	    			+ "<h3 style='color: #27ae60; margin-top: 0;'>Reservation Details: </h3>"
	    			+ "<p><strong>Reservation Code:</strong> " + dineInReservation.getReservationCode() + "</P>"
	    			+ "<p><strong>Customer Name:</strong> " + dineInReservation.getCustomerName() + "</P>"
	    			+ "<p><strong>Phone Number:</strong> " + dineInReservation.getPhoneNumber() + "</P>"
	    			+ "<p><strong>Email:</strong> " + dineInReservation.getEmail() + "</P>"
	    			+ "<p><strong>Date & Time:</strong> " + dineInReservation.getFormattedReservationDateTime() + "</P>"
	    			+ "<p><strong>Number of Guests:</strong> " + dineInReservation.getNumberOfGuests() + "</P>"
	    			+ "<p><strong>Seating Preference:</strong> " + dineInReservation.getSeatingPreference() + "</P>"
	    			+ "</div>"
	    			+ "<div style='margin: 20px 0;'>"
	    			+ "<h4 style='color: #2c3e50;'>Additional Information: </h4>"
	    			+ "<p><strong>Special Occasion:</strong> " + dineInReservation.getSpecialOccasion() != null ? dineInReservation.getSpecialOccasion() : "None" + "</p>"
	    			+ "<p><strong>Dietary Restrictions:</strong> " + dineInReservation.getDietaryRestrictions() != null ? dineInReservation.getDietaryRestrictions() : "None" + "</p>"
	    			+ "</div>"
	    			+ "<div style='background: #e8f4fd; padding: 15px; border-radius:5px; margin: 20px 0;'>"
	    			+ "<p style='margin:0; color: #2c3e50;'><strong>Action Required: </strong>Please review and confirm this reservation in the staff portal.</p>"
	    			+ "</div>"
	    			+ "p style='text-align: center; color: #7f8c8d; font-size: 14px'>"
	    			+ "This is an automated notification from ABC Modern Kitchen Reservation System."
	    			+ "</p>"
	    			+ "</div>"
	    			+ "</body>"
	    			+ "</html>";
	    	message.setContent(emailContent, "text/html");
	    	Transport.send(message);
	    	System.out.println("[SUCCESS] New reservation notification sent to staff: " + staffEmail);
	    	
	    } catch(Exception e) {
	    	System.out.println("[ERROR] Failed to send new reservation notification: " + e.getMessage());
			e.printStackTrace();
	    }   
	}
	
	public static void sendReservationCancellaionNotification(String staffEmail, DineInReservation dineInReservation) {
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
	    	message.addRecipient(Message.RecipientType.TO, new InternetAddress(staffEmail));
	    	
	    	message.setSubject("Reservation Cancelled: - " + dineInReservation.getReservationCode());
	    	
	    	String emailContent = "<html>"
	    			+ "<body style='font-family: Arial, sans-sarif; line-height: 1.6; color: #333;'>"
	    			+ "<div style='max-width:600px; margin: 0 auto; padding: 20px; border: 1px solod #ddd; border-radius: 10px;'>"
	    			+ "<h2 style='color: #2c3e50; text-align: centrt;'>ABC Modern Kitchen - Reservation Cancelled</h2>"
	    			+ "<div style='background: #f8f9fa; padding: 15px; border-radius: 5px; margin: 20px 0'>"
	    			+ "<h3 style='color: #27ae60; margin-top: 0;'>Cancelled Reservation Details: </h3>"
	    			+ "<p><strong>Reservation Code:</strong> " + dineInReservation.getReservationCode() + "</P>"
	    			+ "<p><strong>Customer Name:</strong> " + dineInReservation.getCustomerName() + "</P>"
	    			+ "<p><strong>Phone Number:</strong> " + dineInReservation.getPhoneNumber() + "</P>"
	    			+ "<p><strong>Email:</strong> " + dineInReservation.getEmail() + "</P>"
	    			+ "<p><strong>Date & Time:</strong> " + dineInReservation.getFormattedReservationDateTime() + "</P>"
	    			+ "<p><strong>Number of Guests:</strong> " + dineInReservation.getNumberOfGuests() + "</P>"
	    			+ "<p><strong>Seating Preference:</strong> " + dineInReservation.getSeatingPreference() + "</P>"
	    			+ "</div>"
	    			+ "<div style='margin: 20px 0;'>"
	    			+ "<h4 style='color: #2c3e50;'>Additional Information: </h4>"
	    			+ "<p><strong>Special Occasion:</strong> " + dineInReservation.getSpecialOccasion() != null ? dineInReservation.getSpecialOccasion() : "None" + "</p>"
	    			+ "<p><strong>Dietary Restrictions:</strong> " + dineInReservation.getDietaryRestrictions() != null ? dineInReservation.getDietaryRestrictions() : "None" + "</p>"
	    			+ "</div>"
	    			+ "p style='text-align: center; color: #7f8c8d; font-size: 14px'>"
	    			+ "This reservation has been cancelled by the customer."
	    			+ "</p>"
	    			+ "</div>"
	    			+ "</body>"
	    			+ "</html>";
	    	message.setContent(emailContent, "text/html");
	    	Transport.send(message);
	    	System.out.println("[SUCCESS] New reservation notification sent to staff: " + staffEmail);
	    	
	    } catch(Exception e) {
	    	System.out.println("[ERROR] Failed to send new reservation notification: " + e.getMessage());
			e.printStackTrace();
	    }
	}
	
	public static void sendReservationStatusUpdateToCustomer(DineInReservation dineInReservation) {
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
	    	message.addRecipient(Message.RecipientType.TO, new InternetAddress(dineInReservation.getEmail()));
	    	
	    	String statusColor = "";
	    	String statusMessage = "";
	    	
	    	switch(dineInReservation.getStatus()) {
	    	case "Confirmed":
	    		statusColor = "#27ae60";
	    		statusMessage = "Your reservation has been confirmed! We loook forward to serving you.";
	    		break;
	    	case "Completed":
	    		statusColor = "#3498db";
	    		statusMessage = "Your reservation has been marked as completed. Thank you for dining with us!.";
	    		break;
	    	case "Cancelled":
	    		statusColor = "#e74c3c";
	    		statusMessage = "Your reservation has been cancelled as per your request.";
	    		break;
	    	default:
	    		statusColor = "#f39c12";
	    		statusMessage = "Your reservation status has been updated.";
	    	}
	    	
	    	message.setSubject("Reservation " + dineInReservation.getStatus() + " - " + dineInReservation.getReservationCode());
	    	
	    	String emailContent = "<html>"
	                + "<body style='font-family: Arial, sans-serif; line-height: 1.6; color: #333;'>"
	                + "<div style='max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px;'>"
	                + "<h2 style='color: #2c3e50; text-align: center;'>ABC Modern Kitchen - Reservation Update</h2>"
	                + "<div style='background: " + statusColor + "; color: white; padding: 15px; border-radius: 5px; margin: 20px 0; text-align: center;'>"
	                + "<h3 style='margin: 0;'>Reservation " + dineInReservation.getStatus() + "</h3>"
	                + "</div>"
	                + "<div style='background: #f8f9fa; padding: 15px; border-radius: 5px; margin: 20px 0;'>"
	                + "<h4 style='color: #2c3e50; margin-top: 0;'>Reservation Details:</h4>"
	                + "<p><strong>Reservation Code:</strong> " + dineInReservation.getReservationCode() + "</p>"
	                + "<p><strong>Customer Name:</strong> " + dineInReservation.getCustomerName() + "</p>"
	                + "<p><strong>Date & Time:</strong> " + dineInReservation.getFormattedReservationDateTime() + "</p>"
	                + "<p><strong>Number of Guests:</strong> " + dineInReservation.getNumberOfGuests() + "</p>"
	                + "<p><strong>Seating Preference:</strong> " + dineInReservation.getDisplaySeatingPreference() + "</p>"
	                + "</div>"
	                + "<div style='margin: 20px 0;'>"
	                + "<p><strong>Status Message:</strong> " + statusMessage + "</p>"
	                + "</div>"
	                + "<div style='background: #e8f4fd; padding: 15px; border-radius: 5px; margin: 20px 0;'>"
	                + "<h4 style='color: #2c3e50; margin-top: 0;'>Contact Information:</h4>"
	                + "<p>If you have any questions, please contact us at:</p>"
	                + "<p><strong>Phone:</strong> +94 112 345 678</p>"
	                + "<p><strong>Email:</strong> info@abcmodernkitchen.com</p>"
	                + "</div>"
	                + "<p style='text-align: center; color: #7f8c8d; font-size: 14px;'>"
	                + "Thank you for choosing ABC Modern Kitchen!"
	                + "</p>"
	                + "</div>"
	                + "</body>"
	                + "</html>";
	    	
	    	message.setContent(emailContent, "text/html");
	    	Transport.send(message);
	    	System.out.println("[SUCCESS] Reservation status update sent to customer: " + dineInReservation.getEmail());
	    	
	    } catch(Exception e) {
	    	System.out.println("[ERROR] Failed to send status update to customer: " + e.getMessage());
	        e.printStackTrace();
	    }
	    
	}
	
	public static void sendPasswordResetEmail(String toEmail, String resetURL) {
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
	    	
	    	message.setSubject("ABC Modern Kitchen - Password Reset Request");
	    	
	    	String emailContent = "<html>"
	    			+ "<head>"
	                + "<style>"
	                + "body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }"
	                + ".container { max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px; }"
	                + ".header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 20px; text-align: center; border-radius: 10px 10px 0 0; }"
	                + ".content { padding: 20px; }"
	                + ".button { display: inline-block; padding: 12px 24px; background: #667eea; color: white; text-decoration: none; border-radius: 5px; margin: 20px 0; }"
	                + ".footer { padding: 20px; text-align: center; font-size: 12px; color: #666; }"
	                + "</style>"
	                + "</head>"
	                + "<body>"
	                + "<div class='container'>"
	                + "<div class='header'>"
	                + "<h1>ABC Modern Kitchen</h1>"
	                + "<h2>Password Reset Request</h2>"
	                + "</div>"
	                + "<div class='content'>"
	                + "<p>Hello,</p>"
	                + "<p>We received a request to reset your password for your ABC Modern Kitchen account.</p>"
	                + "<p>Click the button below to reset your password:</p>"
	                + "<p style='text-align: center;'>"
	                + "<a href='" + resetURL + "' class='button'>Reset Password</a>"
	                + "</p>"
	                + "<p>This link will expire in 24 hours for security reasons.</p>"
	                + "<p>If you didn't request a password reset, please ignore this email.</p>"
	                + "</div>"
	                + "<div class='footer'>"
	                + "<p>This is an automated message. Please do not reply to this email.</p>"
	                + "<p>&copy; 2025 ABC Modern Kitchen. All rights reserved.</p>"
	                + "</div>"
	                + "</div>"
	                + "</body>"
	                + "</html>";
	    	
	    	message.setContent(emailContent, "text/html");
	    	Transport.send(message);
	    	System.out.println("[SUCCESS] Password Reset email sent to: " + toEmail);
	    	  	
	    } catch(Exception e) {
	    	System.out.println("[ERROR] Failed to send password reset email: " + e.getMessage());
	        e.printStackTrace();
	    }
	    
	}
	
	public static void sendPasswordResetSuccessEmail(String toEmail, String name) {
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
	    	
	    	message.setSubject("ABC Modern Kitchen - Password Reset Successfull");
	    	
	    	String emailContent = "<html>"
	    			+ "<head>"
	                + "<style>"
	                + "body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }"
	                + ".container { max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px; }"
	                + ".header { background: linear-gradient(135deg, #4CAF50 0%, #45a049 100%); color: white; padding: 20px; text-align: center; border-radius: 10px 10px 0 0; }"
	                + ".content { padding: 20px; }"
	                + ".footer { padding: 20px; text-align: center; font-size: 12px; color: #666; }"
	                + "</style>"
	                + "</head>"
	                + "<body>"
	                + "<div class='container'>"
	                + "<div class='header'>"
	                + "<h1>ABC Modern Kitchen</h1>"
	                + "<h2>Password Reset Successful</h2>"
	                + "</div>"
	                + "<div class='content'>"
	                + "<p>Hello " + (name != null ? name : "there") + ",</p>"
	                + "<p>Your password has been successfully reset.</p>"
	                + "<p>If you did not make this change, please contact our support team immediately.</p>"
	                + "<p>For security reasons, we recommend that you:</p>"
	                + "<ul>"
	                + "<li>Use a strong, unique password</li>"
	                + "<li>Enable two-factor authentication if available</li>"
	                + "<li>Avoid using the same password across multiple sites</li>"
	                + "</ul>"
	                + "</div>"
	                + "<div class='footer'>"
	                + "<p>This is an automated message. Please do not reply to this email.</p>"
	                + "<p>&copy; 2025 ABC Modern Kitchen. All rights reserved.</p>"
	                + "</div>"
	                + "</div>"
	                + "</body>"
	    			+ "</html>";
	    	message.setContent(emailContent, "text/html");
	    	Transport.send(message);
	    	System.out.println("[SUCCESS] Password Reset Success email sent to: " + toEmail);
  	
	    } catch(Exception e) {
	    	System.out.println("[ERROR] Failed to send password reset success email: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
}

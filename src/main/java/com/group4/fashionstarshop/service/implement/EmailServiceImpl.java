package com.group4.fashionstarshop.service.implement;

import com.group4.fashionstarshop.model.Seller;
import com.group4.fashionstarshop.model.User;
import com.group4.fashionstarshop.model.VerificationToken;
import com.group4.fashionstarshop.repository.UserRepository;
import com.group4.fashionstarshop.service.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendConfirmEmail(User user, String token) throws MessagingException {
    	 String recipientAddress = user.getEmail();
	        String verifyLink = "http://localhost:5454/api" + "/register/confirmation?token=" + token;
	        String subject = "Register Confirmation";
	        String mailContent = "<p> Hi, "+ user.getClientName()+ ", </p>"+
	                "<p>Thank you for registering with us,"+"" +
	                "Please, follow the link below to complete your registration.</p>"+
	                "<a href=\"" +verifyLink+ "\">Verify this link to activate your account</a>"+
	                "<p> Thank you <br> Fashion Star Registration Portal Service";
	        MimeMessage message = emailSender.createMimeMessage();
	        var messageHelper = new MimeMessageHelper(message);
	        messageHelper.setTo(recipientAddress);
	        messageHelper.setSubject(subject);
	        messageHelper.setText(mailContent,true);
	        emailSender.send(message);
    }
    @Override
    public void sendSellerConfirmEmail(Seller seller, String token) throws MessagingException {
    	 String recipientAddress = seller.getEmail();
	        String verifyLink = "http://localhost:5454/api" + "/seller/register/confirmation?token=" + token;
	        String subject = "Register Confirmation";
	        String mailContent = "<p> Hi, "+ seller.getSellerName()+ ", </p>"+
	                "<p>Thank you for becoming our partner,"+"" +
	                "Please, follow the link below to complete your registration.</p>"+
	                "<a href=\"" +verifyLink+ "\">Verify this link to activate your account</a>"+
	                "<p> Thank you <br> Fashion Star Registration Portal Service";
	        MimeMessage message = emailSender.createMimeMessage();
	        var messageHelper = new MimeMessageHelper(message);
	        messageHelper.setTo(recipientAddress);
	        messageHelper.setSubject(subject);
	        messageHelper.setText(mailContent,true);
	        emailSender.send(message);
    }
    @Override
    public void sendPaymentEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter formatCurrentDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = currentDate.format(formatCurrentDate);
        String to  = user.getEmail();
        String subject = "Payment has been successful";
        String text = "Hello " + user.getClientName() + "\n" + "\n" +



























































































































                "Your order has been placed successfully at " + formattedDate + "\n" +
                "Wishing you always have great experiences when shopping at FashionStar" + "\n" + "\n" +
                "View your Order: http://localhost:3000/order";
        message.setFrom("noreply@baeldung.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

	@Override
	public void sendPasswordReset(User user, String token) throws UnsupportedEncodingException, MessagingException {
	        String recipientAddress = user.getEmail();
	        String verifyLink = "http://localhost:5454/api" + "/register/reset-password?token=" + token;
	        String subject = "Reset Password Confirmation";
	        String mailContent = "<p> Hi, "+ user.getEmail()+ ", </p>"+
	                "<p>You send the request of forgetting password,"+"" +
	                "Please, follow the link below to get new password.</p>"+
	                "<a href=\"" +verifyLink+ "\">Verify this link</a>"+
	                "<p> Thank you <br> Fashion Star Registration Portal Service";
	        MimeMessage message = emailSender.createMimeMessage();
	        var messageHelper = new MimeMessageHelper(message);
	        messageHelper.setTo(recipientAddress);
	        messageHelper.setSubject(subject);
	        messageHelper.setText(mailContent,true);
	        emailSender.send(message);
		
	}

	@Override
	public void sendSellerPasswordReset(Seller seller, String token)
			throws UnsupportedEncodingException, MessagingException {
		 	String recipientAddress = seller.getEmail();
	        String verifyLink = "http://localhost:5454/api" + "/register/reset-password?token=" + token;
	        String subject = "Reset Password Confirmation";
	        String mailContent = "<p> Hi, Seller "+ seller.getSellerName()+ ", </p>"+
	                "<p>You send the request of forgetting password,"+"" +
	                "Please, follow the link below to get new password.</p>"+
	                "<a href=\"" +verifyLink+ "\">Verify this link</a>"+
	                "<p> Thank you <br> Fashion Star Registration Portal Service";
	        MimeMessage message = emailSender.createMimeMessage();
	        var messageHelper = new MimeMessageHelper(message);
	        messageHelper.setTo(recipientAddress);
	        messageHelper.setSubject(subject);
	        messageHelper.setText(mailContent,true);
	        emailSender.send(message);
		
	}
    
    
}

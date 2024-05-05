package com.group4.fashionstarshop.utils.listener;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.group4.fashionstarshop.model.User;
import com.group4.fashionstarshop.service.UserService;
import com.group4.fashionstarshop.utils.RegistrationCompleteEvent;
import java.io.UnsupportedEncodingException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent>{
	 private final UserService userService;
	 private final JavaMailSender mailSender;
	 private  User user;
	 @Override
	 public void onApplicationEvent(RegistrationCompleteEvent event) {
	     // 1. Get the newly registered user
	     user = event.getUser();
	     //2. Create a verification token for the user
	     String verificationToken = UUID.randomUUID().toString();
	     //3. Save the verification token for the user
	     userService.saveUserVerificationToken(user, verificationToken);
	     //4. Build the verification url to be sent to the user
	     String url = event.getApplicationUrl()+"/register/verifyEmail?token="+verificationToken;
	     //5. Send the email.
	     try {
	         sendPasswordResetVerificationEmail(url);
	     } catch (MessagingException | UnsupportedEncodingException e) {
	         throw new RuntimeException(e);
	     }
	     log.info("Click the link to verify your registration :  {}", url);
	 }

	 public void sendVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {
	     String subject = "Email Verification";
	     String senderName = "User Registration Portal Service";
	     String mailContent = "<p> Hi, "+ user.getEmail()+ ", </p>"+
	             "<p>Thank you for registering with us,"+"" +
	             "Please, follow the link below to complete your registration.</p>"+
	             "<a href=\"" +url+ "\">Verify your email to activate your account</a>"+
	             "<p> Thank you <br> Users Registration Portal Service";
	     MimeMessage message = mailSender.createMimeMessage();
	     var messageHelper = new MimeMessageHelper(message);
	     messageHelper.setFrom("phamquocchien160501@gmail.com", senderName);
	     messageHelper.setTo(user.getEmail());
	     messageHelper.setSubject(subject);
	     messageHelper.setText(mailContent, true);
	     mailSender.send(message);
	 }

	 public void sendPasswordResetVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {
		    // Kiểm tra xem user đã được gán giá trị chưa
		    if (user == null) {
		        throw new IllegalArgumentException("User must be set before sending password reset verification email");
		    }

		    String subject = "Password Reset Request Verification";
		    String senderName = "User Registration Portal Service";
		    String mailContent = "<p> Hi, "+ user.getEmail()+ ", </p>"+
		            "<p><b>You recently requested to reset your password,</b>"+"" +
		            "Please, follow the link below to complete the action.</p>"+
		            "<a href=\"" +url+ "\">Reset password</a>"+
		            "<p> Fashion Shop";
		    MimeMessage message = mailSender.createMimeMessage();
		    var messageHelper = new MimeMessageHelper(message);
		    messageHelper.setFrom("phamquocchien160501@gmail.com", senderName);
		    messageHelper.setTo(user.getEmail());
		    messageHelper.setSubject(subject);
		    messageHelper.setText(mailContent, true);
		    mailSender.send(message);
		}

}

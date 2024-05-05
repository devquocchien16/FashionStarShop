package com.group4.fashionstarshop.service;

import java.io.UnsupportedEncodingException;

import com.group4.fashionstarshop.model.Seller;
import com.group4.fashionstarshop.model.User;
import com.group4.fashionstarshop.model.VerificationToken;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendConfirmEmail(User user, String token) throws MessagingException;
    void sendPaymentEmail(User user);
    void sendPasswordReset(User user, String token) throws UnsupportedEncodingException, MessagingException;
    void sendSellerPasswordReset(Seller seller, String token) throws UnsupportedEncodingException, MessagingException;
	void sendSellerConfirmEmail(Seller seller, String token) throws MessagingException;
}

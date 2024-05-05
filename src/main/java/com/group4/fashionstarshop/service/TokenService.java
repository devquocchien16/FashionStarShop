package com.group4.fashionstarshop.service;

import java.util.Optional;

import com.group4.fashionstarshop.model.PasswordResetToken;
import com.group4.fashionstarshop.model.User;

public interface TokenService {
	void createPasswordResetTokenForUser(User user, String passwordToken);
	
	String validatePasswordResetToken(String passwordResetToken);
	
	Optional<User> findUserByPasswordToken(String passwordResetToken);
	
	PasswordResetToken findPasswordResetToken(String token);
	
	
}

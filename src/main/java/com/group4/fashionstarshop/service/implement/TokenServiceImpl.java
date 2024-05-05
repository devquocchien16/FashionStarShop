package com.group4.fashionstarshop.service.implement;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group4.fashionstarshop.model.PasswordResetToken;
import com.group4.fashionstarshop.model.User;
import com.group4.fashionstarshop.repository.PasswordResetRepository;
import com.group4.fashionstarshop.repository.VerificationTokenRepository;
import com.group4.fashionstarshop.service.TokenService;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService{

	@Autowired
	private PasswordResetRepository tokenRepository;
	@Override
	public void createPasswordResetTokenForUser(User user, String passwordToken) {
	    PasswordResetToken passwordResetToken = new PasswordResetToken(passwordToken, user);
	    tokenRepository.save(passwordResetToken);
	}


	@Override
	public String validatePasswordResetToken(String passwordResetToken) {
        PasswordResetToken passwordToken = tokenRepository.findByToken(passwordResetToken);
        if(passwordToken == null){
            return "Invalid verification token";
        }
       User user = passwordToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((passwordToken.getExpiryDate().getTime()-calendar.getTime().getTime())<= 0){
            return "Link already expired, resend link";
        }
        return "valid";
	}

	@Override
	public Optional<User> findUserByPasswordToken(String passwordResetToken) {
		// TODO Auto-generated method stub
		return Optional.ofNullable(tokenRepository.findByToken(passwordResetToken).getUser());
	}

	@Override
	public PasswordResetToken findPasswordResetToken(String token) {
		// TODO Auto-generated method stub
		return tokenRepository.findByToken(token);
	}



}
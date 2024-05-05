package com.group4.fashionstarshop.service;

import java.util.Optional;

import com.group4.fashionstarshop.model.ResetSellerToken;
import com.group4.fashionstarshop.model.Seller;
import com.group4.fashionstarshop.model.User;

public interface ResetSellerTokenService {
	void createPasswordResetTokenForSeller(Seller seller, String passwordToken);
	
	String validatePasswordResetToken(String resetTokenSeller);
	
	Optional<Seller> findSellerrByPasswordToken(String passwordResetToken);
	
	ResetSellerToken findPasswordResetToken(String token);
}

package com.group4.fashionstarshop.service.implement;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group4.fashionstarshop.model.PasswordResetToken;
import com.group4.fashionstarshop.model.ResetSellerToken;
import com.group4.fashionstarshop.model.Seller;
import com.group4.fashionstarshop.model.User;
import com.group4.fashionstarshop.repository.ResetSellerTokenRepository;
import com.group4.fashionstarshop.service.ResetSellerTokenService;
@Service
public class ResetSellerTokenServiceImpl implements ResetSellerTokenService{
	@Autowired
	private ResetSellerTokenRepository resetSellerTokenRepository;
	@Override
	public void createPasswordResetTokenForSeller(Seller seller, String passwordToken) {
		ResetSellerToken resetSellerToken = new ResetSellerToken(passwordToken, seller);
		resetSellerTokenRepository.save(resetSellerToken);
		
	}

	@Override
	public String validatePasswordResetToken(String resetTokenSeller) {
		  ResetSellerToken passwordToken = resetSellerTokenRepository.findByToken(resetTokenSeller);
	        if(passwordToken == null){
	            return "Invalid verification token";
	        }
	       Seller seller = passwordToken.getSeller();
	        Calendar calendar = Calendar.getInstance();
	        if ((passwordToken.getExpiryDate().getTime()-calendar.getTime().getTime())<= 0){
	            return "Link already expired, resend link";
	        }
	        return "valid";
	}

	@Override
	public Optional<Seller> findSellerrByPasswordToken(String passwordResetToken) {
		// TODO Auto-generated method stub
		return Optional.ofNullable(resetSellerTokenRepository.findByToken(passwordResetToken).getSeller());
	}

	@Override
	public ResetSellerToken findPasswordResetToken(String token) {
		// TODO Auto-generated method stub
		return resetSellerTokenRepository.findByToken(token);
	}
	
}

package com.group4.fashionstarshop.service;

import java.util.Optional;

import com.group4.fashionstarshop.dto.SellerDTO;
import com.group4.fashionstarshop.dto.SellerLoginDTO;
import com.group4.fashionstarshop.dto.SellerRegisterDTO;
import com.group4.fashionstarshop.dto.SellerResetDTO;
import com.group4.fashionstarshop.dto.UserResetDTO;
import com.group4.fashionstarshop.model.Seller;
import com.group4.fashionstarshop.model.VerifiyTokenSeller;
import jakarta.servlet.http.HttpServletResponse;
import com.group4.fashionstarshop.request.UpdateSellerRequest;

public interface SellerService {
    String login(SellerLoginDTO sellerLoginDTO);
    Seller register(SellerRegisterDTO sellerRegisterDTO);
    Seller findById(Long id);
    Seller findUserByJwt(String jwt);
    void changePassword(Seller seller, String newPassword);
    String validatePasswordResetToken(String token);
    void createPasswordResetTokenForSeller(Seller seller);
    boolean oldPasswordIsValid(Seller seller, String oldPassword);
//    User findUserByJwt(String jwt);
    Optional<Seller> findSellerByPasswordToken(String token);
//	Seller findByEmail(String email);
	Seller findByEmail(SellerResetDTO userResetDTO);
	void createVerificationToken(Seller seller);
	void verifyToken(VerifiyTokenSeller verificationToken, HttpServletResponse response) throws Exception;
	boolean checkExpiryDate(VerifiyTokenSeller verificationToken);
	SellerDTO updateSeller(Long sellerId, UpdateSellerRequest request);
	SellerDTO findSeller(Long sellerId);
	SellerDTO findBySellerId(Long id);
}

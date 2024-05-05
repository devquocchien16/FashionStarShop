package com.group4.fashionstarshop.controller;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.group4.fashionstarshop.dto.PasswordRequestUtil;
import com.group4.fashionstarshop.dto.SellerResetDTO;
import com.group4.fashionstarshop.dto.UserResetDTO;
import com.group4.fashionstarshop.dto.VerificationTokenDTO;
import com.group4.fashionstarshop.model.PasswordResetToken;
import com.group4.fashionstarshop.model.ResetSellerToken;
import com.group4.fashionstarshop.model.Seller;
import com.group4.fashionstarshop.model.User;
import com.group4.fashionstarshop.repository.ResetSellerTokenRepository;
import com.group4.fashionstarshop.repository.SellerRepository;
import com.group4.fashionstarshop.repository.UserRepository;
import com.group4.fashionstarshop.service.EmailService;
import com.group4.fashionstarshop.service.SellerService;
import com.group4.fashionstarshop.service.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api")
public class PasswordController {
	@Autowired
	private ResetSellerTokenRepository resetSellerTokenRepository;
	@Autowired
    private SellerService sellerService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private SellerRepository sellerRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userRepository;
	@PostMapping("/seller/password-reset-request")
	public ResponseEntity<?> resetPasswordRequest(@RequestBody SellerResetDTO userResetDTO, final HttpServletRequest servletRequest) throws MessagingException, UnsupportedEncodingException {
	    Seller seller = sellerService.findByEmail(userResetDTO);
	    if (seller == null) {
	        return ResponseEntity.notFound().build();
	    }
	    
	    sellerService.createPasswordResetTokenForSeller(seller);    
	    ResetSellerToken passwordResetToken = resetSellerTokenRepository.findBySellerIm(seller);
	    
	    return ResponseEntity.ok(passwordResetToken); // Trả về đối tượng ResetSellerToken chứa mã đặt lại mật khẩu
	}
    @GetMapping("/seller/reset-password")
	public ResponseEntity<String> resetPassword(@RequestParam("token") String token, HttpServletResponse response) {
        // Lưu token vào cookie
        Cookie cookie = new Cookie("resetPasswordToken", token);
        cookie.setHttpOnly(false);
        cookie.setPath("/api/register/reset-password");
        cookie.setSecure(true);
        response.addCookie(cookie);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "http://localhost:3000/changepass");
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }


	@PostMapping("/seller/reset-password")
	public String resetPassword(@RequestBody PasswordRequestUtil passwordRequestUtil,
			@RequestParam("token") String token) {
		String tokenVerificationResult = sellerService.validatePasswordResetToken(token);
		if (!tokenVerificationResult.equalsIgnoreCase("valid")) {
			return "Invalid token password reset token";
		}
		Optional<Seller> theUser = sellerService.findSellerByPasswordToken(token);
		if (theUser.isPresent()) {
			sellerService.changePassword(theUser.get(), passwordRequestUtil.getNewPassword());
			return "Password has been reset successfully";
		}
		return "Invalid password reset token";
	}
	@PutMapping("/register/user/change-password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordRequestUtil changePasswordRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();
        User user = userRepository.findByEmail(currentEmail);
        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mật khẩu cũ không đúng");
        }
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("Mật khẩu đã được thay đổi thành công");

	}
      
}

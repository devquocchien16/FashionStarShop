package com.group4.fashionstarshop.controller;

import com.group4.fashionstarshop.dto.AdminDTO;
import com.group4.fashionstarshop.dto.AdminRegister;
import com.group4.fashionstarshop.dto.PasswordRequestUtil;
import com.group4.fashionstarshop.dto.SellerLoginDTO;
import com.group4.fashionstarshop.dto.SellerRegisterDTO;
import com.group4.fashionstarshop.dto.UserLoginDTO;
import com.group4.fashionstarshop.dto.UserRegisterDTO;
import com.group4.fashionstarshop.dto.UserResetDTO;
import com.group4.fashionstarshop.model.Admin;
import com.group4.fashionstarshop.model.PasswordResetToken;
import com.group4.fashionstarshop.model.Seller;
import com.group4.fashionstarshop.model.User;
import com.group4.fashionstarshop.model.VerificationToken;
import com.group4.fashionstarshop.model.VerifiyTokenSeller;
import com.group4.fashionstarshop.repository.PasswordResetRepository;
import com.group4.fashionstarshop.repository.SellerVerifyRepository;
import com.group4.fashionstarshop.repository.UserRepository;
import com.group4.fashionstarshop.repository.VerificationTokenRepository;
import com.group4.fashionstarshop.service.AdminService;
import com.group4.fashionstarshop.service.EmailService;
import com.group4.fashionstarshop.service.SellerService;
import com.group4.fashionstarshop.service.UserService;
import com.group4.fashionstarshop.utils.listener.RegistrationCompleteEventListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api")
public class AuthController {
	@Autowired
	private UserService userService;
	@Autowired
	private SellerService sellerService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private VerificationTokenRepository verificationTokenRepository;
	@Autowired
	private SellerVerifyRepository sellerVerifyRepository;
	@Autowired
	private PasswordResetRepository passwordResetRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RegistrationCompleteEventListener eventListener;
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserLoginDTO userLoginDto) {
		String token = userService.login(userLoginDto);
		return ResponseEntity.ok(token);
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UserRegisterDTO userRegisterDto) throws MessagingException {
		User user = userService.register(userRegisterDto);
		userService.createVerificationToken(user);
		VerificationToken verificationToken = verificationTokenRepository.findByUser(user);
		String token = verificationToken.getToken();
		try {
			emailService.sendConfirmEmail(user, token);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok(user);
	}

	@GetMapping("/register/confirmation")
	public void verifyUser(@RequestParam(name = "token") String token, HttpServletResponse response) throws Exception {
		VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
		userService.verifyToken(verificationToken, response);
		response.sendRedirect("http://localhost:3000/confirm?status=success");
	}
	@PostMapping("/register/password-reset-request")
	public ResponseEntity<?> resetPasswordRequest(@RequestBody UserResetDTO userResetDTO, final HttpServletRequest servletRequest) throws MessagingException, UnsupportedEncodingException {
	    User user = userService.findByEmail(userResetDTO);
	    passwordResetRepository.deleteByUser(user);
	    userService.createPasswordResetTokenForUser(user);
	    
	    PasswordResetToken passwordResetToken = passwordResetRepository.findByUserIm(user);
	    String token = passwordResetToken.getToken();
	    emailService.sendPasswordReset(user, token);
	    
	    return ResponseEntity.ok(user);
	}
	@PostMapping("/seller/login")
	public ResponseEntity<?> loginSeller(@RequestBody SellerLoginDTO sellerLoginDto) {
		String token = sellerService.login(sellerLoginDto);
		return ResponseEntity.ok(token);
	}

	@PostMapping("/seller/register")
	public ResponseEntity<?> registerSeller(@RequestBody SellerRegisterDTO sellerRegisterDto) {
		Seller seller = sellerService.register(sellerRegisterDto);
		sellerService.createVerificationToken(seller);
		VerifiyTokenSeller verificationToken = sellerVerifyRepository.findBySeller(seller);
		String token = verificationToken.getToken();
		try {
			emailService.sendSellerConfirmEmail(seller, token);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok(seller);
	}
	@GetMapping("/seller/register/confirmation")
	public void verifySeller(@RequestParam(name = "token") String token, HttpServletResponse response) throws Exception {
		VerifiyTokenSeller verificationToken = sellerVerifyRepository.findByToken(token);
		sellerService.verifyToken(verificationToken, response);
		response.sendRedirect("http://localhost:3000/confirm?status=success");
	}
	@PostMapping("/login/admin")
	public ResponseEntity<?> loginAdmin(@RequestBody AdminDTO adminDto) {
		String token = adminService.login(adminDto);
		return ResponseEntity.ok(token);
	}

	@PostMapping("/register/admin")
	public ResponseEntity<?> registerAdmin(@RequestBody AdminRegister adminRegister) {
		Admin admin = adminService.register(adminRegister);
		return ResponseEntity.ok(admin);
	}
	
	@GetMapping("/register/reset-password")
	public ResponseEntity<String> resetPassword(@RequestParam("token") String token, HttpServletResponse response) {
        // Lưu token vào cookie
        Cookie cookie = new Cookie("resetPasswordToken", token);
        cookie.setHttpOnly(false);
        cookie.setPath("/");
       
        response.addCookie(cookie);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "http://localhost:3000/changepass");
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }


	@PostMapping("/register/reset-password")
	public String resetPassword(@RequestBody PasswordRequestUtil passwordRequestUtil,
			@RequestParam("token") String token) {
		String tokenVerificationResult = userService.validatePasswordResetToken(token);
		if (!tokenVerificationResult.equalsIgnoreCase("valid")) {
			return "Invalid token password reset token";
		}
		Optional<User> theUser = Optional.ofNullable(userService.findUserByPasswordToken(token));
		if (theUser.isPresent()) {
			userService.changePassword(theUser.get(), passwordRequestUtil.getNewPassword());
			return "Password has been reset successfully";
		}
		return "Invalid password reset token";
	}

	@PostMapping("/register/change-password")
	public String changePassword(@RequestBody PasswordRequestUtil requestUtil) {
		User user = userService.findByEmail(requestUtil.getEmail()).get();
		if (!userService.oldPasswordIsValid(user, requestUtil.getOldPassword())) {
			return "Incorrect old password";
		}
		userService.changePassword(user, requestUtil.getNewPassword());
		return "Password changed successfully";
	}

	public String applicationUrl(HttpServletRequest request) {
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}
	
	
}

package com.group4.fashionstarshop.service.implement;

import com.group4.fashionstarshop.configuration.security.JwtTokenUtil;
import com.group4.fashionstarshop.enums.Role;
import com.group4.fashionstarshop.dto.UserRegisterDTO;
import com.group4.fashionstarshop.dto.UserResetDTO;
import com.group4.fashionstarshop.dto.VerificationTokenDTO;
import com.group4.fashionstarshop.dto.UserDTO;
import com.group4.fashionstarshop.dto.UserEditDTO;
import com.group4.fashionstarshop.dto.UserLoginDTO;
import com.group4.fashionstarshop.model.PasswordResetToken;
import com.group4.fashionstarshop.model.User;
import com.group4.fashionstarshop.model.VerificationToken;
import com.group4.fashionstarshop.exception.RedirectException;
import com.group4.fashionstarshop.repository.PasswordResetRepository;
import com.group4.fashionstarshop.repository.UserRepository;
import com.group4.fashionstarshop.repository.VerificationTokenRepository;
import com.group4.fashionstarshop.configuration.security.JwtUserDetailsService;
import com.group4.fashionstarshop.service.TokenService;
import com.group4.fashionstarshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    @Autowired
    private VerificationTokenRepository tokenRepository;
    @Autowired
    private PasswordResetRepository passwordTokenRepository;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TokenService tokenService;
    
    @Override
    public String login(UserLoginDTO userLoginDto) {
        User user = userRepository.findByEmail(userLoginDto.getEmail());

        if(user == null){
            throw new UsernameNotFoundException("User not found");
        } else if(!user.isEnabled()){
            throw new UsernameNotFoundException("User has not been confirmed");
        }
        if(!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())){
            throw new AuthenticationServiceException("Wrong password");
        }
        return jwtTokenUtil.generateToken(user);
    }

    @Override
    @Transactional
    public User register(UserRegisterDTO userRegisterDto) {
        jwtUserDetailsService.loadUserByUsername(userRegisterDto.getEmail());
        String password = userRegisterDto.getPassword();
        User user = new User();
        user.setClientName(userRegisterDto.getClientName());
        user.setEmail(userRegisterDto.getEmail());
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("ROLE_".concat(Role.USER.toString()));
        userRepository.save(user);
        return user;
    }

    @Override
    public User findById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return user;
    }

    @Override
    public void createVerificationToken(User user) {
        Date expireDate = calculateExpiryDate(60*24);
        String token = UUID.randomUUID().toString();
        VerificationToken userToken = new VerificationToken();
        userToken.setUser(user);
        userToken.setToken(token);
        userToken.setExpiryDate(expireDate);
        tokenRepository.save(userToken);
    }
	@Override
	public void createPasswordResetTokenForUser(User user) {
		Date exDate = calculateExpiryDate(60*24);
		String resetToken = UUID.randomUUID().toString();
		PasswordResetToken passwordResetToken = new PasswordResetToken();
		passwordResetToken.setUser(user);
		passwordResetToken.setToken(resetToken);
		passwordResetToken.setExpiryDate(exDate);
		passwordTokenRepository.save(passwordResetToken);
		
	}
    @Override
    public boolean checkExpiryDate(VerificationToken verificationToken){
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return true;
        } else{
            return false;
        }
    }

    @Override
    public void verifyToken(VerificationToken verificationToken, HttpServletResponse response) throws Exception{

        if(verificationToken != null ){
            boolean isExpired = checkExpiryDate(verificationToken);
            if(!isExpired){
                User user = verificationToken.getUser();
                user.setEnabled(true);
                userRepository.save(user);
            } else {
                throw new RedirectException("Expired token", "http://localhost:3000/confirm?status=error");
            }
        } else {
            throw new RedirectException("Invalid token", "http://localhost:3000/confirm?status=error");
        }
    }
    
    public Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

	@Override
	public void createPasswordResetTokenForUser(UserResetDTO userDTO, String token) {
		User user = new User();
        user.setEmail(userDTO.getEmail());
        userRepository.save(user);

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(calculateExpiryDate(60 * 24)); // 24 hours
        tokenRepository.save(verificationToken);
        sendEmail(user.getEmail(), "Password Reset", "Token: " + token);
		
	}
	
    private void sendEmail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

    private boolean isTokenExpired(VerificationToken verificationToken) {
        return verificationToken.getExpiryDate().before(new Date());
    }

	@Override
	public void resetPassword(VerificationTokenDTO tokenDTO, String newPassword) {
		 PasswordResetToken passwordResetToken = passwordTokenRepository.findByToken(tokenDTO.getToken());
	        if (passwordResetToken != null  && 
	        		passwordResetToken.getUser().getId().equals(tokenDTO.getUserId())) {
	            User user = passwordResetToken.getUser();
	            user.setPassword(newPassword); // Ideally, encrypt this password
	            userRepository.save(user);
	            passwordTokenRepository.delete(passwordResetToken);
	        }
		
	}
	@Override
    public User getUserInfoFromToken(String token) {
		Long id = jwtTokenUtil.getIdFromJwtToken(token);  // Assuming you create a method to extract email
	    Optional<User> user = userRepository.findById(id);  // Assuming you have a method to find users by email

	    return user.orElse(null);
    }
	@Override
	public User findUserByJwt(String jwt) {
		 Long id = jwtTokenUtil.getIdFromJwtToken(jwt);  
	        Optional<User> user = userRepository.findById(id);
	        return user.orElse(null); 
	}
	 @Override
	 public User findByEmail(UserResetDTO userResetDTO) {
	        return userRepository.findByEmail(userResetDTO.getEmail());
	 }
	@Override
	public void createNewVerificationToken(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changePassword(User user, String newPassword) {
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
	}
	 @Override
	    public User findUserByPasswordToken(String token) {
	        return tokenService.findUserByPasswordToken(token).get();
	    }
	@Override
	public String validatePasswordResetToken(String token) {
		// TODO Auto-generated method stub
		return tokenService.validatePasswordResetToken(token);
	}



	@Override
	public boolean oldPasswordIsValid(User user, String oldPassword) {
		// TODO Auto-generated method stub
		return passwordEncoder.matches(oldPassword, user.getPassword());
	}

	@Override
	public void saveUserVerificationToken(User theUser, String token) {
		 var verificationToken = new VerificationToken(token, theUser);
	     tokenRepository.save(verificationToken);
		
	}


	@Override
	public PasswordResetToken findPasswordResetTokenForUser(User user) {
		// TODO Auto-generated method stub
		return passwordTokenRepository.findByUserIm(user);
	}

	@Override
	public void updatePasswordResetToken(PasswordResetToken token) {
		passwordTokenRepository.save(token);
		
	}

	@Override
	public Optional<User> findByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findOneByEmail(email);
	}

	@Override
	public UserDTO editUserProfile(Long id, UserEditDTO userEditDTO) {
	    Optional<User> userOptional = userRepository.findById(id);
	    if (!userOptional.isPresent()) {
	        throw new IllegalArgumentException("User not found with id: " + id);
	    }

	    User user = userOptional.get();
	    // Sử dụng trực tiếp giá trị Date từ DTO
	    user.setBirthday(userEditDTO.getBirthday());
	    user.setPhone(userEditDTO.getPhone());
	    user.setClientName(userEditDTO.getClientName());
	    user.setGender(false); // Giả sử đây là một giá trị cố định

	    User updatedUser = userRepository.save(user);
	    UserDTO updatedUserDTO = new UserDTO();
	    updatedUserDTO.setId(updatedUser.getId());
	    updatedUserDTO.setClientName(updatedUser.getClientName());
	    updatedUserDTO.setPhone(updatedUser.getPhone());
	    updatedUserDTO.setBirthday(updatedUser.getBirthday()); // Sử dụng trực tiếp ngày sinh từ user

	    return updatedUserDTO;
	}
}
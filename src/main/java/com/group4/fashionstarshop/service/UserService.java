package com.group4.fashionstarshop.service;

import com.group4.fashionstarshop.dto.UserDTO;
import com.group4.fashionstarshop.dto.UserEditDTO;
import com.group4.fashionstarshop.dto.UserLoginDTO;
import com.group4.fashionstarshop.dto.UserRegisterDTO;
import com.group4.fashionstarshop.dto.UserResetDTO;
import com.group4.fashionstarshop.dto.VerificationTokenDTO;
import com.group4.fashionstarshop.model.PasswordResetToken;
import com.group4.fashionstarshop.model.User;
import com.group4.fashionstarshop.model.VerificationToken;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    String login(UserLoginDTO userLoginDTO);
    User register(UserRegisterDTO userRegisterDTO);
    User findById (Long id);
    void createVerificationToken(User user);
    boolean checkExpiryDate(VerificationToken verificationToken);
    void saveUserVerificationToken(User theUser, String token);
    void verifyToken(VerificationToken verificationToken, HttpServletResponse response) throws Exception;
    void createPasswordResetTokenForUser(UserResetDTO userDTO, String token);
    void resetPassword(VerificationTokenDTO tokenDTO, String newPassword);
    void createNewVerificationToken(User user);
    void changePassword(User user, String newPassword);
    String validatePasswordResetToken(String token);
    void createPasswordResetTokenForUser(User user);
    boolean oldPasswordIsValid(User user, String oldPassword);
    User findUserByJwt(String jwt);
    User findByEmail(UserResetDTO userResetDTO);
    User findUserByPasswordToken(String token);
    User getUserInfoFromToken(String token);
    PasswordResetToken findPasswordResetTokenForUser(User user);
	void updatePasswordResetToken(PasswordResetToken token);
	Optional<User> findByEmail(String email);
	UserDTO editUserProfile(Long id, UserEditDTO userEditDTO);
}

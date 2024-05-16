package com.group4.fashionstarshop.controller;

import com.group4.fashionstarshop.configuration.security.JwtTokenUtil;
import com.group4.fashionstarshop.converter.UserConverter;
import com.group4.fashionstarshop.dto.UserDTO;
import com.group4.fashionstarshop.dto.UserEditDTO;
import com.group4.fashionstarshop.model.User;
import com.group4.fashionstarshop.repository.UserRepository;
import com.group4.fashionstarshop.service.UserService;

import lombok.extern.slf4j.Slf4j;

import org.hibernate.cfg.Environment;
//import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Env;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/users")
public class UserController {
	@Autowired
	private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    private org.springframework.core.env.Environment env;
//    @Autowired
//    private Logger logger;
    @GetMapping("/{user_id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("user_id") Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        System.out.println(user.getPhone());
        UserDTO userDTO = userConverter.convertEntityToDTO(user);
       
        return ResponseEntity.ok(userDTO);
    }
    @GetMapping("/getUserByEmailCatLam")
	public UserDTO getUserByEmailCatLam(@RequestParam String email) {
		var result = userService.findUserByEmail(email);
		return result;
	}
    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String jwt) {
//        // Lấy thông tin người dùng từ token
    	User userDto = userService.findUserByJwt(jwt);
    	System.out.println("Jwt" + jwt );
    	return ResponseEntity.ok(userDto);
//        User userInfoDTO = userService.getUserInfoFromToken(token);
//        if (userInfoDTO != null) {
//            return ResponseEntity.ok(userInfoDTO);
//        } else {
//            return ResponseEntity.badRequest().body("Không thể lấy thông tin người dùng.");
//        }
    }
    @PostMapping("/edit/{user_id}")
    public ResponseEntity<?> editUserProfile(@PathVariable("user_id") Long userId, @RequestBody UserEditDTO userEditDTO) {
        // Lấy username từ token
  
           UserDTO userDTO = userService.editUserProfile(userId, userEditDTO);
            return ResponseEntity.ok(userDTO);

    }
    @GetMapping("/getToken")
    public ResponseEntity<String> getToken(@RequestHeader HttpHeaders header){
    	log.debug("token Start");
    	String userId = jwtTokenUtil.getUserIdByToken(header, env.getProperty("token.secret"));
    	
    	return ResponseEntity.status(HttpStatus.OK).body(userId);
    }
//    @GetMapping("/profile")
//    public ResponseEntity<User> findUserChat(@RequestHeader("Authorization") String jwt) {
//        logger.debug("JWT token received: {}", jwt);
//        User user = userService.findUserByJwt(jwt);
//        // Rest of your code
//		return ResponseEntity.ok(user);
//    }
}

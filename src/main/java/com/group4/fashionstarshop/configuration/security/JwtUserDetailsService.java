package com.group4.fashionstarshop.configuration.security;

import com.group4.fashionstarshop.enums.Role;
import com.group4.fashionstarshop.model.Admin;
import com.group4.fashionstarshop.model.Seller;
import com.group4.fashionstarshop.model.User;
import com.group4.fashionstarshop.model.VerificationToken;
import com.group4.fashionstarshop.repository.AdminRepository;
import com.group4.fashionstarshop.repository.SellerRepository;
import com.group4.fashionstarshop.repository.UserRepository;
import com.group4.fashionstarshop.repository.VerificationTokenRepository;
import com.group4.fashionstarshop.service.EmailService;
import com.group4.fashionstarshop.service.UserService;

import jakarta.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private EmailService emailService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            VerificationToken token = verificationTokenRepository.findByUser(user);
            if(user.isEnabled()){
                throw new UsernameNotFoundException("Email has already been registered");
            } else{
                boolean isExpired;
                Calendar cal = Calendar.getInstance();
                if ((token.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
                    isExpired = true;
                } else{
                    isExpired = false;
                }
                String tokenUID = token.getToken();
                if(isExpired){
                    try {
						emailService.sendConfirmEmail(user, tokenUID);
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    return null;
                } else {
                    throw new UsernameNotFoundException("Email has not been enabled");
                }
            }
        }else {
                return null;
        }
    }

    public UserDetails loadSellerByEmail(String email) throws UsernameNotFoundException {
        Seller seller = sellerRepository.findByEmail(email);

        if (seller != null) {
            throw new UsernameNotFoundException("Email has already been registered");
        }else{
            return null;
        }
    }
    
    public UserDetails loadAdminByEmail(String email) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByEmail(email);

        if (admin != null) {
            throw new UsernameNotFoundException("Email has already been registered");
        }else{
            return null;
        }
    }


}

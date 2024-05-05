package com.group4.fashionstarshop.configuration.security;

import com.group4.fashionstarshop.model.Admin;
import com.group4.fashionstarshop.model.Seller;
import com.group4.fashionstarshop.model.User;
import com.group4.fashionstarshop.repository.AdminRepository;
import com.group4.fashionstarshop.repository.SellerRepository;
import com.group4.fashionstarshop.repository.UserRepository;
import com.group4.fashionstarshop.configuration.security.JwtUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";

    private static final String BEARER = "Bearer ";


    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private AdminRepository adminRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader(AUTHORIZATION);

        String id = null;
        String jwtToken = null;
        // JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
        if (requestTokenHeader != null && requestTokenHeader.startsWith(BEARER)) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                if(jwtTokenUtil.isTokenExpired(jwtToken)){
                    throw new ExpiredJwtException(null, null, "Jwt expired");
                }
                id = jwtTokenUtil.getIdFromToken(jwtToken);

            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }

        //Once we get the token validate it.
        if (id != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            User user = userRepository.findById(Long.parseLong(id)).orElse(null);
            Seller seller = sellerRepository.findById(Long.parseLong(id)).orElse(null);
            Admin admin = adminRepository.findById(Long.parseLong(id)).orElse(null);

            if (user == null && seller == null && admin == null) {
                throw new UsernameNotFoundException("User not found!");
            }

            // Check if the user is an admin
            boolean isAdmin = (admin != null);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;
            if (user == null) {
                usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        seller != null ? seller : admin,
                        null,
                        Collections.singleton(new SimpleGrantedAuthority((seller != null ? seller.getRole() : admin.getRole()).toString())));
            } else if (isAdmin) {
                // Assign admin role
                usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        admin,
                        null,
                        Collections.singleton(new SimpleGrantedAuthority(admin.getRole().toString())));
            } else {
                // For regular users
                usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        Collections.singleton(new SimpleGrantedAuthority(user.getRole().toString())));
            }
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        chain.doFilter(request, response);
    }

}


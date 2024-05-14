package com.group4.fashionstarshop.configuration;

import com.group4.fashionstarshop.configuration.security.JwtAuthenticationEntryPoint;
import com.group4.fashionstarshop.configuration.security.JwtAuthenticationFilter;
import com.group4.fashionstarshop.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.requestMatchers("/api/register/**", "/api/login/**", "/api/seller/**").permitAll()
				.requestMatchers("/api/products/**", "/api/cart-lines/**", "/api/cart/**", "/api/search/**",
						"/api/payments/**", "/api/save-for-later/**", "/api/stores/**", "/api/variant/**",
						"/api/image/**", "/api/product-detail/**", "/api/option-value/**", "/api/option/**", "/api/category/**",
						"/api/store-category/**", "/api/bullet/**", "/api/attribute/**",
						"/api/payments/**", "/api/requestReset/**","/api/resetPassword/**","/api/chats/**","/api/orders/**", "/api/admin/**","/api/users/getUserByEmailCatLam")
				.permitAll()
				.requestMatchers(HttpMethod.GET, "/api/products", "/api/cart-lines/**", "/api/search/**", "/api/reviews/**","/api/product-detail/**", "/api/sellers/**", "/api/stores/**", "/api/payments/**","/api/orders/**","api/admin/**").permitAll()
				.requestMatchers(HttpMethod.POST, "/api/chats/**","/api/products", "/api/cart-lines/**", "/api/search/**","/api/product-detail/**", "/api/reviews/**", "/api/sellers/**", "/api/stores/**", "/api/payments/**","/api/orders/**","api/admin/**").permitAll()
				.requestMatchers("/api/users/**").hasRole(Role.USER.toString())
				.requestMatchers("/api/sellers/**").hasAuthority(Role.SELLER.toString())
//				.requestMatchers("/api/admin/**").hasRole(Role.ADMIN.toString())
				
				// Any other request must be authenticated
				.anyRequest().authenticated().and()
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
	

	
	


}
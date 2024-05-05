package com.group4.fashionstarshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.group4.fashionstarshop.model.PasswordResetToken;
import com.group4.fashionstarshop.model.User;

import jakarta.transaction.Transactional;

@Repository
public interface PasswordResetRepository extends JpaRepository<PasswordResetToken, Long>{
	PasswordResetToken findByToken(String passwordResetToken);

	Optional<PasswordResetToken> findByUser(User user);

	@Transactional
	void deleteByUser(User user);

	@Query("SELECT t FROM PasswordResetToken t WHERE t.user = :user")
	PasswordResetToken findByUserIm(@Param("user") User user);
	@Query("SELECT COUNT(p) FROM PasswordResetToken p WHERE p.user = :user")
	int countByUser(@RequestParam("user") User user);
	
}

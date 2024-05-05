package com.group4.fashionstarshop.repository;

import com.group4.fashionstarshop.model.User;
import com.group4.fashionstarshop.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {
    VerificationToken findByUser(User user);
    VerificationToken findByToken(String token);
}

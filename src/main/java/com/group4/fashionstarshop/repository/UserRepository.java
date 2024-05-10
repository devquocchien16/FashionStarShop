package com.group4.fashionstarshop.repository;

import com.group4.fashionstarshop.dto.UserDTO;
import com.group4.fashionstarshop.model.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
	@Query("SELECT u FROM User u WHERE u.id IN :ids")
    List<User> findAllByIdsIn(List<Long> ids);
	Optional<User> findById(Long id);
	Long countByEnabled(boolean enabled);
    Optional<User> findOneByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.clientName = :clientName")
    Optional <User> findUsersByClientName(String clientName);
    // Trả về User
    default User findByEmail(String email) {
        return findOneByEmail(email).orElse(null);
    }
    @Query("SELECT u FROM User u WHERE LOWER(u.clientName) LIKE LOWER(CONCAT('%', :clientName, '%'))")
    List<User> findByClientNameContainingIgnoreCase(String clientName);
    List<User> findByClientNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String clientName, String email);
}

package com.group4.fashionstarshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group4.fashionstarshop.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long>{
	Admin findByEmail(String email);
}

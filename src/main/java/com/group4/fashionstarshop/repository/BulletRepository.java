package com.group4.fashionstarshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group4.fashionstarshop.model.Bullet;

@Repository
public interface BulletRepository extends JpaRepository<Bullet, Long> {
}

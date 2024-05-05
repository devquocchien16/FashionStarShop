package com.group4.fashionstarshop.service;

import com.group4.fashionstarshop.model.Bullet;

import java.util.List;

public interface BulletService {
    List<Bullet> createBullet(List<String> bulletlist, Long productId);

}

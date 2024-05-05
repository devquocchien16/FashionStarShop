package com.group4.fashionstarshop.converter;

import com.group4.fashionstarshop.dto.BulletDTO;
import com.group4.fashionstarshop.model.Bullet;

import java.util.List;

public interface BulletConverter {
    List<BulletDTO> entitiesToDTOs(List<Bullet> element);
    BulletDTO entityToDTO(Bullet element);
    Bullet dtoToEntity(BulletDTO element);
}

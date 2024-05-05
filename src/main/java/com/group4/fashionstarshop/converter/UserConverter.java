package com.group4.fashionstarshop.converter;

import com.group4.fashionstarshop.dto.StoreDTO;
import com.group4.fashionstarshop.dto.UserDTO;
import com.group4.fashionstarshop.dto.UserLoginDTO;
import com.group4.fashionstarshop.model.Store;
import com.group4.fashionstarshop.model.User;

import java.util.List;

public interface UserConverter {
    UserDTO convertEntityToDTO(User user);
    List<UserLoginDTO> convertEntitiesToDTOs(List<User> users);
    List<UserDTO> entitiesToDTOs(List<User> element);
    UserDTO entityToDTO(User element);

}

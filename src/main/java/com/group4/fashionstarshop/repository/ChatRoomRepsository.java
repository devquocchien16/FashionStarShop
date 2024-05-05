package com.group4.fashionstarshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.group4.fashionstarshop.model.ChatRoom;
import com.group4.fashionstarshop.model.Seller;
import com.group4.fashionstarshop.model.User;

public interface ChatRoomRepsository extends JpaRepository<ChatRoom, Long>{
	public List<ChatRoom> findBySellersId(Long id);
	@Query("SELECT c FROM ChatRoom c WHERE :user = c.user AND :seller MEMBER OF c.sellers")
	public ChatRoom findChatByUserAndSeller(@Param("user") User user, @Param("seller") Seller seller);



}

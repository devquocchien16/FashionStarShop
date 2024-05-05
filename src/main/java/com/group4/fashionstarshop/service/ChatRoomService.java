package com.group4.fashionstarshop.service;

import java.util.List;

import com.group4.fashionstarshop.model.ChatRoom;
import com.group4.fashionstarshop.model.Seller;
import com.group4.fashionstarshop.model.User;

public interface ChatRoomService {
	public ChatRoom createChat(Seller seller, User user);
	
	public ChatRoom findByChatId(Long id) throws Exception;
	
	public List<ChatRoom> findSellerChat(Long sellerId);
}

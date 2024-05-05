package com.group4.fashionstarshop.service.implement;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group4.fashionstarshop.model.ChatRoom;
import com.group4.fashionstarshop.model.Seller;
import com.group4.fashionstarshop.model.User;
import com.group4.fashionstarshop.repository.ChatRoomRepsository;
import com.group4.fashionstarshop.service.ChatRoomService;
@Service
public class ChatServiceImpl implements ChatRoomService{

	@Autowired
	private ChatRoomRepsository chatRoomRepsository;
	
	@Override
	public ChatRoom createChat(Seller seller, User user) {
		ChatRoom isExist = chatRoomRepsository.findChatByUserAndSeller(user, seller);
		if (isExist != null) {
			return isExist;
		}
		ChatRoom chatRoom = new ChatRoom();
		chatRoom.getSellers().add(seller);
		chatRoom.setUser(user);;
		chatRoom.setTimestamp(LocalDateTime.now());
		return chatRoomRepsository.save(chatRoom);
		
	}

	@Override
	public ChatRoom findByChatId(Long id) throws Exception {
		Optional<ChatRoom> chatRoom = chatRoomRepsository.findById(id);
		
		if (chatRoom.isEmpty()) {
			throw new Exception("Chat is Not Found");
		}
		return chatRoom.get();
	}

	@Override
	public List<ChatRoom> findSellerChat(Long sellerId) {
		// TODO Auto-generated method stub
		return chatRoomRepsository.findBySellersId(sellerId);
	}

}

package net.javaguides.springboot.service;

import java.util.List;

import net.javaguides.springboot.entity.MessageRecorderEntity;

public interface MessageRecorderServiceInterface {
	
	void save(String sender, String receiver, String messageContent);
	
	List<MessageRecorderEntity> findAllByReceiverName(String receiverName);
}

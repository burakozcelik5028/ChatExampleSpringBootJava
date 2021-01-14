package net.javaguides.springboot.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.springboot.entity.MessageEntity;
import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.service.MessageRecorderServiceInterface;
import net.javaguides.springboot.service.UserServiceInterface;
import net.javaguides.springboot.entity.MessageRecorderEntity;

@RestController
@CrossOrigin
public class MessageController {
	
	private final UserServiceInterface userServiceInterface;
	private final SimpMessagingTemplate simpMessagingTemplate;
	private final MessageRecorderServiceInterface messageRecorder;
	
	@Autowired
	public MessageController(UserServiceInterface userServiceInterface, SimpMessagingTemplate simpMessagingTemplate, MessageRecorderServiceInterface messageRecorder) {
		this.userServiceInterface = userServiceInterface;
		this.simpMessagingTemplate = simpMessagingTemplate;
		this.messageRecorder = messageRecorder;
	}
	
	@MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable String to, MessageEntity message) {
		User destination = userServiceInterface.findByUserName(to);
		User sender = userServiceInterface.findByUserName(message.getFromLogin());
		int control = 0;
        if (destination.getId() != null) {
        	try {
        		Boolean blockControl = userServiceInterface.blockControl(destination.getUserName(), sender.getUserName());
        		if(blockControl.equals(Boolean.TRUE)) {
        			control=1;
        			throw new Exception("You can not sent message " + to);
        		}
        	}
        	catch(Exception e) {
        		if(control==0) {
        			simpMessagingTemplate.convertAndSend("/topic/messages/" + to, message);
        			messageRecorder.save(message.getFromLogin(), destination.getUserName(), message.getMessage());
        		}
        		control=1;
        	}
        	if(control==0) {
        		simpMessagingTemplate.convertAndSend("/topic/messages/" + to, message);
    			messageRecorder.save(message.getFromLogin(), destination.getUserName(), message.getMessage());
        	}
        	
            
        }
    }
	
	@GetMapping("/mymessages")
	public List<MessageRecorderEntity> getMyMessages(@RequestParam String receiverName) throws Exception{
		User user = userServiceInterface.findByUserName(receiverName);
		if(user.getId() == null) {
			throw new Exception("There is no user with this " + user.getUserName() + "user name!");
		}
		List<MessageRecorderEntity> response = messageRecorder.findAllByReceiverName(receiverName);
		return response;
		
	}

}

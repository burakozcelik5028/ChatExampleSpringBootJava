package net.javaguides.springboot.api;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import net.javaguides.springboot.entity.MessageRecorderEntity;
import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.service.MessageRecorderServiceInterface;
import net.javaguides.springboot.service.UserServiceInterface;

class MessageControllerTest {
	
	@Mock
	private UserServiceInterface userServiceInterface;
	@Mock
	private MessageRecorderServiceInterface messageRecorder;
	
	@InjectMocks
	private MessageController controller;

	@Before
	void setUp() {
	}

	@Test
	void testGetMyMessages() throws Exception {
		
	}

}

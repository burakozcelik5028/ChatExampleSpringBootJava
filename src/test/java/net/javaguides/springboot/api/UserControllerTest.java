package net.javaguides.springboot.api;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import net.javaguides.springboot.repository.ActivityLogEntityRepo;
import net.javaguides.springboot.service.UserServiceInterface;

class UserControllerTest {
	UserServiceInterface userServiceInterface;
	ActivityLogEntityRepo activityLogEntityRepo;
	UserController controller;
	
	@Before
	void setUp() {
		userServiceInterface = createMock(UserServiceInterface.class);
		activityLogEntityRepo = createMock(ActivityLogEntityRepo.class);
		controller = new UserController(userServiceInterface, activityLogEntityRepo);
	}

	@Test
	void test() {
	}
	
	public static <T> T createMock(Class<T> classToMock) {
		return Mockito.mock(classToMock);
	}
}

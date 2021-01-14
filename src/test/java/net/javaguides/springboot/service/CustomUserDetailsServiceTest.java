package net.javaguides.springboot.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CustomUserDetailsServiceTest {

	UserServiceInterface userServiceInterface;
	CustomUserDetailsService sevice;
	
	@Before
	void setUp() {
		userServiceInterface = createMock(UserServiceInterface.class);
		sevice = new CustomUserDetailsService();
	}

	@Test
	void test() {
		fail("Not yet implemented");
	}
	
	public static <T> T createMock(Class<T> classToMock) {
		return Mockito.mock(classToMock);
	}
}

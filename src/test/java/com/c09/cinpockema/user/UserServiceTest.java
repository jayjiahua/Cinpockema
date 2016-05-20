package com.c09.cinpockema.user;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.c09.cinpockema.Application;
import com.c09.cinpockema.entities.User;
import com.c09.cinpockema.entities.repositories.UserRepository;
import com.c09.cinpockema.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class UserServiceTest {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Before
	public void setUp() {
		userRepository.deleteAll();
	}
	
	@Test
	public void listUsers() {
		User user = new User();
		user.setUsername("test-list");
		user.setPassword("test-list");
		
		userRepository.save(user);

		assertTrue(((ArrayList<User>) userService.listUsers()).size() == 1);
	}
	
	
	@Test
	public void getUserById() {
		User user = new User();
		user.setUsername("test-get");
		user.setPassword("test-get");
		
		User expectedUser = userRepository.save(user);
		
		User actualUser = userService.getUserById(user.getId());
		
		assertEquals(expectedUser.getId(), actualUser.getId());
		assertEquals(expectedUser.getUsername(), actualUser.getUsername());
	}
	
	@Test
	public void createUser() {
		User user = new User();
		user.setUsername("test-create");
		user.setPassword("test-create");
		
		User actualUser = userService.createUser(user);
		
		User expectedUser = userRepository.findByUsername("test-create");
		
		assertEquals(expectedUser.getId(), actualUser.getId());
		assertEquals(expectedUser.getUsername(), actualUser.getUsername());
	}
	
	@Test
	public void updateUser() {
		User user = new User();
		user.setUsername("test-update");
		user.setPassword("test-update");
		user.setNickname("test-update");
		user = userRepository.save(user);

		assertEquals("test-update", user.getNickname());
		
		user.setNickname("test-update-again");
		
		User actualUser = userService.updateUser(user);
		
		User expectedUser = userRepository.findOne(user.getId());
		
		assertEquals(expectedUser.getNickname(), actualUser.getNickname());
	}
	
	@Test
	public void deleteUser() {
		User user = new User();
		user.setUsername("test-delete");
		user.setPassword("test-delete");
		user.setNickname("test-delete");
		user = userRepository.save(user);
		
		assertNotNull(userRepository.findByUsername("test-delete"));
		
		userService.deleteUserById(user.getId());
		
		assertNull(userRepository.findByUsername("test-delete"));
		
	}
	
	@Test
	public void loadUserByUsername() {
		User user = new User();
		user.setUsername("test-load");
		user.setPassword("test-load");
		userRepository.save(user);
		
		thrown.expect(UsernameNotFoundException.class);
		userService.loadUserByUsername("foobar");
		
		User actualUser2 = (User) userService.loadUserByUsername("test-load");
		
		assertNotNull(actualUser2);
	}
	
	@After
	public void tearDown() {
		userRepository.deleteAll();
	}
}

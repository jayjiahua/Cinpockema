package com.c09.cinpockema.user.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.c09.cinpockema.user.entities.User;
import com.c09.cinpockema.user.entities.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
	
	public UserService() {
		super();
	}
	
	public User createUser(User user) {
		return userRepository.save(user);
	}    
	
	public Iterable<User> listUsers() {
		return userRepository.findAll();
	}
	
	public User getUserById(long id) {   
		return userRepository.findOne(id);  
	}
		
	public User updateUser(User user) {   
		return userRepository.save(user);   
	}    
	
	public void deleteUserById(long id) {   
		userRepository.delete(id);   
	}    
	
	public User getUserByUsername(String username) {   
		return userRepository.findByUsername(username);  
	}
	
}

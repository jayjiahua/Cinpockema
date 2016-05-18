package com.c09.cinpockema.service;

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

import com.c09.cinpockema.entities.User;
import com.c09.cinpockema.entities.repositories.UserRepository;

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
	
	public User create(User user) {
		return userRepository.save(user);
	}    
	
	public Iterable<User> findAll() {
		return userRepository.findAll();
	}
	
	public User findById(long id) {   
		return userRepository.findOne(id);  
	}    
		
	public User update(User user) {   
		return userRepository.save(user);   
	}    
	
	public void deleteById(long id) {   
		userRepository.delete(id);   
	}    
	
	public User findByUsername(String username) {   
		return userRepository.findByUsername(username);  
	}
	
	
}

package com.c09.cinpockema.service;

import java.sql.Timestamp;
import java.util.UUID;

import org.hibernate.jpa.criteria.expression.SearchedCaseExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.c09.cinpockema.entities.User;
import com.c09.cinpockema.entities.repositories.UserRepository;

@Service
public class SessionService {
	
	@Autowired
	UserRepository userRepository;
		
	public User getCurrentUser() {
		Authentication authentication = (Authentication) SecurityContextHolder.getContext()
		    .getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
			return null;
		} else {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			return userRepository.findByUsername(userDetails.getUsername());
		}
	}
	
	public void login() {
		// Do nothing, just return the cookies
		return;
	}
	
	public void logout() {
		// Actually I don't know what should I do...
		return;
	}
	
}

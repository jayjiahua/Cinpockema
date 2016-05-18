package com.c09.cinpockema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.c09.cinpockema.entities.User;
import com.c09.cinpockema.service.SessionService;

@RestController
@RequestMapping("/session")
public class SessionController {
	
	@Autowired
	private SessionService sessionService;
	
    @RequestMapping(value={"", "/"}, method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority({'admin', 'user'})")
    public User getCurrentUser() {
    	return sessionService.getCurrentUser();
    }
    
    // curl localhost:8080/api/session -H "Content-Type:application/json" -u user:user -d "{}"
    @RequestMapping(value={"", "/"}, method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority({'admin', 'user'})")
    public void loginUser() {
    	// Do nothing, just return the cookies
    }
}

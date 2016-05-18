package com.c09.cinpockema.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.c09.cinpockema.entities.User;
import com.c09.cinpockema.service.SessionService;
import com.c09.cinpockema.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	SessionService sessionService;
	
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority({'admin', 'user'})")
    public User getUserById(@PathVariable("id") long id) {
    	return userService.findById(id);
    }
	
    @RequestMapping(value={"", "/"}, method=RequestMethod.GET)
    @PreAuthorize("hasAuthority('admin')")
    public Iterable<User> getAllUser() {
    	User user = sessionService.getCurrentUser();
    	return userService.findAll();
    }
    
    // curl localhost:8080/api/user  -H "Content-Type: application/json" -d "{\"username\": \"test-user\", \"password\":\"test123\"}"
    @RequestMapping(value={"", "/"}, method=RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("permitAll()")
    public User registUser(@Valid @RequestBody User user) {
    	return userService.create(user);
    }
    
}

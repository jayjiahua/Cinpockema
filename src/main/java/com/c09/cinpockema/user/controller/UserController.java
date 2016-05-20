package com.c09.cinpockema.user.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.c09.cinpockema.user.entities.User;
import com.c09.cinpockema.user.service.SessionService;
import com.c09.cinpockema.user.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	SessionService sessionService;
	
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority({'admin', 'user'})")
    public User getUserById(@PathVariable("id") long id) {
    	return userService.getUserById(id);
    }
	
    @RequestMapping(value={"", "/"}, method=RequestMethod.GET)
    @PreAuthorize("hasAuthority('admin')")
    public Iterable<User> listUsers() {
    	return userService.listUsers();
    }
    
    // curl localhost:8080/api/users  -H "Content-Type: application/json" -d "{\"username\": \"test-user\", \"password\":\"test123\"}"
    @RequestMapping(value={"", "/"}, method=RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("permitAll()")
    public User createUser(@Valid @RequestBody User user) {
    	return userService.createUser(user);
    }
    
}

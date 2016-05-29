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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@Api(value="用户模块", description="用户实体的CURD操作")
@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	SessionService sessionService;
	
	@ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority({'admin', 'user'})")
    public User getUserById(@PathVariable("id") long id) {
    	return userService.getUserById(id);
    }
	
	@ApiOperation(value="获取用户列表", notes="")	
    @RequestMapping(value={"", "/"}, method=RequestMethod.GET)
    @PreAuthorize("hasAuthority('admin')")
    public Iterable<User> listUsers() {
    	return userService.listUsers();
    }
    
    // curl localhost:8080/api/users  -H "Content-Type: application/json" -d "{\"username\": \"test-user\", \"password\":\"test123\"}"
	@ApiOperation(value="创建用户", notes="根据User对象创建用户")
	@RequestMapping(value={"", "/"}, method=RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("permitAll()")
    public User createUser(@Valid @RequestBody User user) {
    	return userService.createUser(user);
    }
    
}

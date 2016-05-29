package com.c09.cinpockema.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.c09.cinpockema.user.entities.User;
import com.c09.cinpockema.user.service.SessionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="会话模块", description="管理用户会话信息")
@RestController
@RequestMapping("/session")
public class SessionController {
	
	@Autowired
	private SessionService sessionService;
	
	@ApiOperation(value="获取当前登陆的用户信息")
    @RequestMapping(value={"", "/"}, method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority({'admin', 'user'})")
    public User getCurrentUser() {
    	return sessionService.getCurrentUser();
    }
    
    // curl localhost:8080/api/session -H "Content-Type:application/json" -u user:user -d "{}"
	@ApiOperation(value="用户登录", notes="将用户名和密码通过HTTP Basic Auth方式提交")
	@RequestMapping(value={"", "/"}, method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority({'admin', 'user'})")
    public void login() {
    	sessionService.login();
    }
    
	@ApiOperation(value="用户登出")
    @RequestMapping(value={"", "/"}, method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority({'admin', 'user'})")
    public void logout() {
    	sessionService.logout();
    }
}

package com.c09.cinpockema.user;

import static org.mockito.AdditionalAnswers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;  
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;  
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;  

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.match.JsonPathRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import com.c09.cinpockema.controller.UserController;
import com.c09.cinpockema.entities.User;
import com.jayway.jsonpath.JsonPath;


@RunWith(SpringJUnit4ClassRunner.class)  
@SpringApplicationConfiguration(classes = MockServletContext.class)  
@WebAppConfiguration  
public class UserControllerTest {

    private MockMvc mvc;  
    

    @Before  
    public void setUp() {  
        UserController userController = Mockito.mock(UserController.class);  
        Mockito.when(userController.createUser(Mockito.any(User.class))).then(returnsFirstArg());  
        
        User user = new User();
        user.setId(99L);
        user.setUsername("test99");
        user.setPassword("test99");
        
        Mockito.when(userController.getUserById(99L)).thenReturn(user);
        
        List<User> users = new LinkedList<User>();
        
        User user2 = new User();
        user2.setId(199L);
        user2.setUsername("test199");
        user2.setPassword("test199");
        
        users.add(user);
        users.add(user2);
        
        Mockito.when(userController.listUsers()).thenReturn(users);
        
        mvc = MockMvcBuilders.standaloneSetup(userController).build();

    }  
  
    @Test
    public void getUserById() throws Exception {
    	ResultActions actions = mvc.perform(MockMvcRequestBuilders.get("/users/99")
				.accept(MediaType.APPLICATION_JSON));  
    	actions.andExpect(status().isOk());
    	actions.andExpect(jsonPath("$.username").value("test99"));
    	
    	ResultActions actions2 = mvc.perform(MockMvcRequestBuilders.get("/users/100")
				.accept(MediaType.APPLICATION_JSON));  
    	actions2.andExpect(status().isOk());
    	actions2.andExpect(content().string(""));
    }
    
    @Test
    public void getUsers() throws Exception {
    	ResultActions actions = mvc.perform(MockMvcRequestBuilders.get("/users")
				.accept(MediaType.APPLICATION_JSON));  
    	actions.andExpect(status().isOk());
    	actions.andExpect(jsonPath("$.[0].username").value("test99"));
    	actions.andExpect(jsonPath("$.[1].username").value("test199"));
    	actions.andExpect(jsonPath("$.[2]").doesNotExist());
    }
    
    @Test  
    public void createUser() throws Exception {
    	String jsonStr = "{\"username\": \"test\", \"password\": \"test\"}";
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.post("/users")
        				.contentType(MediaType.APPLICATION_JSON)
        				.content(jsonStr)
        				.accept(MediaType.APPLICATION_JSON));  

        actions.andExpect(status().isCreated());
        actions.andExpect(jsonPath("$.username").value("test"));        
        actions.andExpect(jsonPath("$.role").value(User.ROLE.user.name()));
        
    	String jsonStr2 = "{\"username\": \"test\"}";
        ResultActions actions2 = mvc.perform(MockMvcRequestBuilders.post("/users")
        				.contentType(MediaType.APPLICATION_JSON)
        				.content(jsonStr2)
        				.accept(MediaType.APPLICATION_JSON));  

        actions2.andExpect(status().isBadRequest());
    }
}  
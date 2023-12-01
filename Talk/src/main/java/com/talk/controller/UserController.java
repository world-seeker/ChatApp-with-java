package com.talk.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.talk.dto.UserDto;
import com.talk.model.ChatMessage;
import com.talk.model.User;
import com.talk.service.UserService;



@Controller
public class UserController {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/registration")
	public String getRegistrationPage(@ModelAttribute("user") UserDto userDto) {
		
		return "register";
		
	}
	
	
	@PostMapping("/registration")
	public String  saveUser(@ModelAttribute("user") UserDto userDto,Model model) {
		userDto.setRole("USER");
		userService.save(userDto);
		model.addAttribute("message","Registered Succesfully");
		return "redirect:/login";
	}
	
	

	
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	
	@GetMapping("/user-page")
	public String userPage (Model model, Principal principal) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute("user",userDetails);
		return "user";
	}
	
	@GetMapping("/admin-page")
	public String adminPage (Model model, Principal principal) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute("user",userDetails);
		List<User> userList = userService.listAllUsers(); // Replace with your actual logic
	    model.addAttribute("users", userList);
		return "admin";
	}
	
	@GetMapping("/chat")
	public String chatpage() {
		return "chat";
	}
	
	//websocket
	  @MessageMapping("/chat.sendMessage")
	    @SendTo("/topic/public")
	    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
	        return chatMessage;
	    }

	    @MessageMapping("/chat.addUser")
	    @SendTo("/topic/public")
	    public ChatMessage addUser(@Payload ChatMessage chatMessage,
	                               SimpMessageHeaderAccessor headerAccessor) {
	        // Add username in web socket session
	        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
	        return chatMessage;
	    }
	
}

package com.talk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.talk.dto.UserDto;
import com.talk.model.User;
import com.talk.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public User save(UserDto userDto) {
		User user = new User(userDto.getEmail(),passwordEncoder.encode(userDto.getPassword()),userDto.getRole(),userDto.getFullname());
		return userRepository.save(user);
	}


	@Override
	public List<User> listAllUsers() {
	   
		
		return userRepository.findAll();
	}
	
	
	
	

}

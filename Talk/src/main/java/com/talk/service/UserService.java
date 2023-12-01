package com.talk.service;

import java.util.List;

import com.talk.dto.UserDto;
import com.talk.model.User;

public interface UserService {

	User save (UserDto userDto);
	
	List<User> listAllUsers();
	
	
	
}

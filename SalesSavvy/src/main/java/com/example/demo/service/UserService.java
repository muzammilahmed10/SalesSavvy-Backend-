package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

	UserRepository userRepository;
	BCryptPasswordEncoder passwordEncoder;
	
	
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = new BCryptPasswordEncoder();
	}
	
	public User register(User user) throws RuntimeException{
	
		if(userRepository.findByUsername(user.getUsername()).isPresent()) {
			throw new RuntimeException("username already taken");
		}
		if(userRepository.findByEmail(user.getEmail()).isPresent()) {
			throw new RuntimeException("Email already registered");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
}

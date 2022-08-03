package com.springboot.lookoutside.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.lookoutside.repository.UserRepository;

@RestController
public class UserController {

	@Autowired
	
	
	@GetMapping("/hi")
	public String hello() {
		return "hi";
	}
}

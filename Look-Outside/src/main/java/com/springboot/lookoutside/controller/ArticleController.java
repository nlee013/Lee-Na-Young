package com.springboot.lookoutside.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleController {

	@GetMapping
	public String hello() {
		
		return "hi";
	}

}

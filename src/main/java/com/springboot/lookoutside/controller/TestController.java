package com.springboot.lookoutside.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	/*
	@GetMapping("/test")
	public String index(@AuthenticationPrincipal PrincipalDetail principal) {
		return principal.getUseNo() + " " + principal.getUserNick() + " " + principal.getUsername() + " " + principal.getPassword();
	}
	*/
	
	@GetMapping("/test")
	public @ResponseBody String index(Authentication authenticataion, @AuthenticationPrincipal OAuth2User oauth) {
		OAuth2User oauth2User = (OAuth2User)authenticataion.getPrincipal();
		System.out.println(oauth2User.getAttributes());
		System.out.println(oauth.getAttributes());
		return "OAuth 세션정보 확인";
	}
	
}

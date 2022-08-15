package com.springboot.lookoutside.config.jwt;

public interface JwtProperties {
	String SECRET = "look-outside"; // 우리 서버만 알고 있는 비밀값
	int EXPIRATION_TIME = 60000*10; // 10분 (1/1000초)
	long REFRESH_TIME = 1000L * 60L * 60L * 24L * 30L * 3L; // 3주
	String TOKEN_PREFIX = "Bearer ";
	String HEADER_STRING = "Authorization";
	String HEADER_STRING2 = "Refresh";
}
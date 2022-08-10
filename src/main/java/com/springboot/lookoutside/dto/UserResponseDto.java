package com.springboot.lookoutside.dto;

import com.springboot.lookoutside.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public class UserResponseDto {
	    private String useEmail;
	    private String useNick;
	    private String useName;
	    private int useNo;

	    public static UserResponseDto of(User user) {
	        return UserResponseDto.builder()
	        		.useNo(user.getUseNo())
	        		.useName(user.getUseName())
	                .useEmail(user.getUseEmail())
	                .useNick(user.getUseNick())
	                .build();
	  }
	
}

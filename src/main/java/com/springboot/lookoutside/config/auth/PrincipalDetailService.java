package com.springboot.lookoutside.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.lookoutside.domain.User;
import com.springboot.lookoutside.repository.UserRepository;

@Service //Bean 등록
public class PrincipalDetailService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	//스프링이 로그인 요청을 가로챌 때, username(useId), password 변수 2개를 가로챔
	//password 부분처리는 알아서 함.
	//username이 DB에 있는지만 확인해서 확인
	@Override
	public UserDetails loadUserByUsername(String useId) throws UsernameNotFoundException {
		User principal = userRepository.findByUseId(useId)
				.orElseThrow(()-> {
					return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다." + useId);
				});
		return new PrincipalDetail(principal); // 시큐리티 세션에 유저정보가 저장됨. 이 때 type UserDetail
	}
}

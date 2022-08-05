package com.springboot.lookoutside.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.lookoutside.domain.User;
import com.springboot.lookoutside.repository.UserRepository;

//서비스 쓰는 이유
//트랜잭션 관리
//

@Service //스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해준다. IoC
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional
	public void signUp(User user) {
		String originUsePw = user.getUsePw(); // 원본 Pw
		String encUsePw = encoder.encode(originUsePw); // 해쉬시킨 Pw
		user.setUsePw(encUsePw);
		userRepository.save(user); //하나의 트랜잭션 쓸수도 있으나 여러개도 가능
	
	}
	
	/*
	@Transactional(readOnly = true) // select 시 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료 ( 정합성 유지 )
	public User signIn(User user) {
		
		return userRepository.findByUseIdAndUsePw(user.getUseId(), user.getUsePw());
		
	}
	*/
	
	public List<User> userList() {
		
		List<User> user = userRepository.findAll();
		
		return user;
	}
}

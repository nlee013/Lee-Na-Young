package com.springboot.lookoutside.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.lookoutside.Service.UserService;
import com.springboot.lookoutside.domain.User;
import com.springboot.lookoutside.dto.ResponseDto;
import com.springboot.lookoutside.repository.UserRepository;

@RestController
@RequestMapping("/user") // 인증 없는 회원들 다 접속가능
public class UserController {

	@Autowired 
	private UserService userService;

	
	//회원가입
	@PostMapping("/sign-up")
	public ResponseDto<Integer> signUp(@RequestBody User user) { // json 타입일시 RequestBody
		System.out.println("UserController : signUp 호출");
		userService.signUp(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1); // Java 오브젝트를 Json으로 변경
	}
	/*
	//로그인
	@PostMapping("/sign-in")
	public ResponseDto<Integer> signIn(@RequestBody User user, HttpSession session) { 
		System.out.println("UserController : signIn 호출");
		User login = userService.signIn(user);
		
		if(login != null) {
			session.setAttribute("pricipal", login);
		}
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1); // data : 1 호출시 성공
	}
	*/
	
	//회원 목록 조회 (전쳬)
	@GetMapping
	public ResponseDto<Integer> userList() {
		System.out.println("UserController : userList 호출");
		userService.userList();
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	
	/*
	//닉네임 중복확인
  
	//ID 중복확인
	
	
	
	//회원정보 수정
	@Transactional //함수 종료시 자동 commit
	@PutMapping("/{use_no}")
	public User updateUser(@PathVariable int use_no, @RequestBody User requestUser) { //json 데이터를 요청 => java Object로 변환 (RequestBody)
		System.out.println("회원번호 : " + use_no);
		System.out.println("비밀번호 : " + requestUser.getUse_pw());
		System.out.println("닉네임 : " + requestUser.getUse_nick());
		System.out.println("E-mail : " + requestUser.getUse_email());
		
		User user = userReposiory.findById(use_no).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다");
		});
		user.setUse_pw(requestUser.getUse_pw());
		user.setUse_email(requestUser.getUse_email());
		
//		userReposiory.save(user); //@Transactional 쓰면 안써도 된다.
		return user; 
	}
	
	//회원 탈퇴, 삭제
	@DeleteMapping("/{use_no}")
	public String deleteUser(@PathVariable int use_no) {
		try {
			userReposiory.deleteById(use_no);			
		} catch (Exception e) {
			return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
		}
		return "회원 삭제";
	}
	*/
	
	
	
}

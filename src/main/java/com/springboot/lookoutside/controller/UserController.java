package com.springboot.lookoutside.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
		System.out.println("UserController : signUp 호출 : 회원가입");
		userService.signUp(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1); // Java 오브젝트를 Json으로 변경
	}
	
	/* 시큐리티로 이관함
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
	
	//
	
	/*
	//닉네임 중복확인
	@GetMapping("/Nickname/{useNick}")
	public ResponseDto<Integer> useNickCheck(@PathVariable String useNick) {
		System.out.println("UserController : useIdCheck 호출 " + useNick);
		userService.useNickCheck(useNick);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	
	//ID 중복확인
	@GetMapping("/Id/{useId}")
	public ResponseDto<Integer> useIdCheck(@PathVariable String useId) {
		System.out.println("UserController : useIdCheck 호출 " + useId);
		userService.useIdCheck(useId);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	*/
	
	//닉네임 중복확인
	@GetMapping("/Nickname/{useNick}")
	public ResponseDto<Boolean> useNickCheck(@PathVariable String useNick) {
		System.out.println("UserController : useIdCheck 호출 " + useNick);
		return new ResponseDto<Boolean>(HttpStatus.OK.value(), userService.useNickCheck(useNick)); // false => 닉네임 가능, true => 닉네임 불가능
	}
  
	//ID 중복확인
	@GetMapping("/Id/{useId}")
	public ResponseDto<Boolean> useIdCheck(@PathVariable String useId) {
		System.out.println("UserController : useIdCheck 호출 " + useId);
		return new ResponseDto<Boolean>(HttpStatus.OK.value(), userService.useIdCheck(useId)); // false => ID 가능, true => ID 불가능
	}
	
	//회원정보 수정
	@PutMapping
	public ResponseDto<Integer> update(@RequestBody User user) {
		userService.updateUser(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	
	//비밀번호변경
	@PutMapping("/NewPw/{useId}")
	public ResponseDto<Integer> update(@RequestBody User user, @PathVariable String useId) {
		userService.newPw(user,useId);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	
	//아이디 찾기
	@GetMapping("/Email/{useEmail}")
	public ResponseDto<String> findMyId(@PathVariable String useEmail) {
		String myId = userService.findMyId(useEmail);
		return new ResponseDto<String>(HttpStatus.OK.value(), myId);
	}
	
	//회원 탈퇴, 삭제
	@DeleteMapping("/{useId}")
	public ResponseDto<String> deleteUser(@PathVariable String useId) {
		String result = userService.deleteUser(useId);
		return new ResponseDto<String>(HttpStatus.OK.value(), result);
	}
	
	/*
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
	*/
	
	
	
}

package com.springboot.lookoutside.controller;

import java.util.Optional;

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

import com.springboot.lookoutside.domain.User;
import com.springboot.lookoutside.dto.ResponseDto;
import com.springboot.lookoutside.service.UserService;

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
	
	//회원 상세정보 보기 (마이페이지 수정할때 뿌려주기위함)
	@GetMapping("/{useId}")
	public ResponseDto<Optional<User>> myPageId(@PathVariable String useId) {
		return new ResponseDto<Optional<User>>(HttpStatus.OK.value(), userService.myPageId(useId));
	}
	
	//비밀버호 복호화(확인)
	@PostMapping("/myPw")
	public ResponseDto<Boolean> checkMyPw(@RequestBody User user) {
		System.out.println("비밀번호 확인");
		return new ResponseDto<Boolean>(HttpStatus.OK.value(), userService.checkMyPw(user));
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
		System.out.println(useEmail);
		String myId = userService.findMyId(useEmail);
		return new ResponseDto<String>(HttpStatus.OK.value(), myId);
	}
	
	//회원 탈퇴, 삭제
	@DeleteMapping("/{useId}")
	public ResponseDto<String> deleteUser(@PathVariable String useId) {
		String result = userService.deleteUser(useId);
		return new ResponseDto<String>(HttpStatus.OK.value(), result);
	}
	
}

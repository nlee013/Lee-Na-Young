package com.springboot.lookoutside.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.lookoutside.domain.User;
import com.springboot.lookoutside.dto.ResponseDto;
import com.springboot.lookoutside.service.ManagerService;

@RestController
@RequestMapping("/manager")
public class ManagerController {
	
	@Autowired 
	private ManagerService managerService;
	
	//회원 목록 전체 조회
	@GetMapping
	public ResponseDto<Page<User>> userList(@PageableDefault(size=3,sort="useCreated",direction = Sort.Direction.DESC ) Pageable pageable) { //가입 최근순 조회 3개
		System.out.println("UserController : userList 호출");
		Page<User> user = managerService.userList(pageable);
		user.getPageable();
		
		return new ResponseDto<Page<User>>(HttpStatus.OK.value(),user);
	}
	
	//회원 권한 수정
	@PutMapping("/{useNo}")
	public ResponseDto<Integer> changeRole(@PathVariable int useNo) {
		managerService.changeRole(useNo);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	
	//회원 선택 삭제
	@DeleteMapping("/user/{useNos}")
	public ResponseDto<String> deleteCheckUser(@PathVariable int[] useNos) {
		String result = managerService.deleteCheckUser(useNos);
		return new ResponseDto<String>(HttpStatus.OK.value(), result);
	}
	
}

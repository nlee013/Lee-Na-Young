package com.springboot.lookoutside.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.lookoutside.domain.User;
import com.springboot.lookoutside.repository.UserRepository;

@Service
public class ManagerService {

	@Autowired
	private UserRepository userRepository;
	
	//회원 목록 조회
	public Page<User> userList(Pageable pageable) {

		Page<User> user = userRepository.findAll(pageable);

		return user;
	}
	
	//회원 선택 삭제
	@Transactional
	public String deleteCheckUser(int[] useNos) {
		
		for(int useNo : useNos) {

			userRepository.findById(useNo).orElseThrow(() -> { 
				return new IllegalArgumentException("삭제에 실패하였습니다. 해당 id는 DB에 없습니다.");
			});

			userRepository.deleteById(useNo);
		}
		return "회원 삭제 완료";
	}
	
	//회원권한수정
	@Transactional
	public void changeRole(String useId) {
		//수정시에는 영속성 컨텍스트 User 오브젝트를 영속화시키고, 영속화된 User 오브젝트를 수정
		// select를 해서 User오브젝트를 DB로 부터 가져오는 이유는 영속화를 하기위함
		// 영속화된 오브젝트를 변경하면 자동으로 DB에 update문 실행
		//User persistance = userRepository.findByUseId(user.getUseId()).orElseThrow(() -> { //user.getUserId -> 세션에 올라와있는 Id이용
		User persistance = userRepository.findByUseId(useId).orElseThrow(() -> { //테스트용
			return new IllegalArgumentException("회원찾기 실패");
		});

		//권한 변경
		if(persistance.getUseRole().equals("USER")) {
			persistance.setUseRole("ADMIN");
		}

		else {
			persistance.setUseRole("USER");
		}

		//회원정보 함수 종료시 서비스 종료 트랜잭션 종료 commit이 자동으로 실행
		//persistance가 변화되면 자동으로 update문 실행
	}
}

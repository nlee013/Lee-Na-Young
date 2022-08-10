package com.springboot.lookoutside.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	//회원가입
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

	//Id 중복확인
	@Transactional
	public boolean useIdCheck(String useId) {
		return userRepository.existsByUseId(useId);
	}

	//Nick 중복확인
	@Transactional
	public boolean useNickCheck(String useNick) {
		return userRepository.existsByUseNick(useNick);
	}

	//Id 찾기
	@Transactional
	public String findMyId(String useEmail) {
		String myId = userRepository.myId(useEmail);
		if(myId == null) {
			myId = "해당 Email로 가입된 ID가 존재하지않습니다.";
		}
		return myId;
	}

	//회원 삭제
	@Transactional
	public String deleteUser(String useId) {

		userRepository.findByUseId(useId).orElseThrow(() -> { 
			return new IllegalArgumentException("삭제에 실패하였습니다. 해당 id는 DB에 없습니다.");
		});

		userRepository.deleteByUseId(useId);
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

	//회원정보수정
	@Transactional
	public void updateUser(User user) {
		//수정시에는 영속성 컨텍스트 User 오브젝트를 영속화시키고, 영속화된 User 오브젝트를 수정
		// select를 해서 User오브젝트를 DB로 부터 가져오는 이유는 영속화를 하기위함
		// 영속화된 오브젝트를 변경하면 자동으로 DB에 update문 실행
		//User persistance = userRepository.findByUseId(user.getUseId()).orElseThrow(() -> { //user.getUserId -> 세션에 올라와있는 Id이용
		User persistance = userRepository.findByUseId("id").orElseThrow(() -> { //테스트용
			return new IllegalArgumentException("회원찾기 실패");
		});
		//비밀번호 수정
		String rawPassword = user.getUsePw();
		String encPassword = encoder.encode(rawPassword);
		persistance.setUsePw(encPassword);

		//이메일 수정
		persistance.setUseEmail(user.getUseEmail());

		//닉네임 수정
		persistance.setUseNick(user.getUseNick());

		//회원정보 함수 종료시 서비스 종료 트랜잭션 종료 commit이 자동으로 실행
		//persistance가 변화되면 자동으로 update문 실행
	}

	//비밀번호 재설정
	@Transactional
	public void newPw(User user, String useId) {
		//수정시에는 영속성 컨텍스트 User 오브젝트를 영속화시키고, 영속화된 User 오브젝트를 수정
		// select를 해서 User오브젝트를 DB로 부터 가져오는 이유는 영속화를 하기위함
		// 영속화된 오브젝트를 변경하면 자동으로 DB에 update문 실행

		User persistance = userRepository.findByUseId(useId).orElseThrow(() -> { //테스트용
			return new IllegalArgumentException("회원찾기 실패");
		});

		//비밀번호 수정
		String rawPassword = user.getUsePw();
		String encPassword = encoder.encode(rawPassword);
		persistance.setUsePw(encPassword);

		//회원정보 함수 종료시 서비스 종료 트랜잭션 종료 commit이 자동으로 실행
		//persistance가 변화되면 자동으로 update문 실행
	}


}

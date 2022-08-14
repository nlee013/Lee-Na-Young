package com.springboot.lookoutside.service;

import java.util.Optional;

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

	//회원가입
	@Transactional
	public void signUp(User user) {
		String originUsePw = user.getUsePw(); // 원본 Pw
		String encUsePw = encoder.encode(originUsePw); // 해쉬시킨 Pw
		user.setUsePw(encUsePw);
		userRepository.save(user); //하나의 트랜잭션 쓸수도 있으나 여러개도 가능

	}

	//마이페이지
	@Transactional
	public Optional<User> myPageInfo(int useNo) {
		/*
		userRepository.findByUseNo(useNo).orElseThrow(() -> { 
			return new IllegalArgumentException("존재하지 않는 아이디");
		});
		*/
		return userRepository.findByUseNo(useNo);
	}
	
	
	//비밀번호 확인
	@Transactional
	public boolean checkMyPw(User user) {
		User persistance = userRepository.findByUseId(user.getUseId()).orElseThrow(() -> { 
			return new IllegalArgumentException("존재하지 않는 아이디");
		});
		
		return encoder.matches(user.getUsePw(), persistance.getUsePw());
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
	
	//Nick 중복확인
	@Transactional
	public boolean useEmailCheck(String useEmail) {
		return userRepository.existsByUseEmail(useEmail);
	}

	//Id 찾기
	@Transactional
	public String findMyId(String useEmail) {
		
		String myId = userRepository.myId(useEmail).orElseThrow(() -> { 
			return new IllegalArgumentException("해당 Email로 가입된 ID는 없습니다.");
		});

		return myId;
	}

	//회원 삭제
	@Transactional
	public String deleteUser(int useNo) {
/*
		userRepository.findByUseNo(useNo).orElseThrow(() -> { 
			return new IllegalArgumentException("삭제에 실패하였습니다. 해당 회원은 존재하지않습니다");
		});
*/
		userRepository.deleteByUseNo(useNo);
		return "회원 삭제 완료";

	}

	//회원정보수정
	@Transactional
	public void updateUser(User user) {
		//수정시에는 영속성 컨텍스트 User 오브젝트를 영속화시키고, 영속화된 User 오브젝트를 수정
		// select를 해서 User오브젝트를 DB로 부터 가져오는 이유는 영속화를 하기위함
		// 영속화된 오브젝트를 변경하면 자동으로 DB에 update문 실행
		//User persistance = userRepository.findByUseId(user.getUseId()).orElseThrow(() -> { //user.getUserId -> 세션에 올라와있는 Id이용
		User persistance = userRepository.findByUseId(user.getUseId()).orElseThrow(() -> { //테스트용
			return new IllegalArgumentException("회원찾기 실패");
		});
		
		//비밀번호 수정
		if(!(user.getUsePw() == null)) {
			String rawPassword = user.getUsePw();
			String encPassword = encoder.encode(rawPassword);
			persistance.setUsePw(encPassword);
		}
		//이메일 수정
		if(!(user.getUseEmail() == null)) {
			
			persistance.setUseEmail(user.getUseEmail());
			
		}
		
		//닉네임 수정
		if(!(user.getUseNick() == null)) {
			
			persistance.setUseNick(user.getUseNick());
			
		}

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

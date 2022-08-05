package com.springboot.lookoutside.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboot.lookoutside.domain.User;

//DAO의 기능
//자동 Bean등록
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{ // <테이블명, PK의 타입>

	//JPA Naming 전략
	// SELECT * FROM user where useId = ?(useId) AND usePw = ?(usePw);
	// User findByUseIdAndUsePw(String useId, String usePw);

	
	//아래처럼 해도된다
	/*
	@Query(value="SELECT * FROM user WHERE use_id = ? AND use_pw = ?", nativeQuery =true)
	User singIn(String use_id, String use_pw);
	*/
	
	Optional<User> findByUseId(String useId);
}

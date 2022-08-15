package com.springboot.lookoutside.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.lookoutside.domain.Region;


//DAO의 기능
//자동 Bean등록
@Repository
public interface RegionRepository extends JpaRepository<Region, String>{ // <테이블명, PK의 타입>

	List<Region> findByRegNoStartingWith(String regNo);
	
}

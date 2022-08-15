package com.springboot.lookoutside.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.lookoutside.domain.Region;
import com.springboot.lookoutside.repository.RegionRepository;

@Service //스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해준다. IoC
public class RegionService {

	@Autowired
	private RegionRepository regionRepository;

	//
	@Transactional
	public List<Region> regionList(String regNo) {
		return regionRepository.findByRegNoStartingWith(regNo);
	}
	
}
	

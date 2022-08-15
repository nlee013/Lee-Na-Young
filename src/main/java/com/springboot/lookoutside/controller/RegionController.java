package com.springboot.lookoutside.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.lookoutside.domain.Region;
import com.springboot.lookoutside.dto.ResponseDto;
import com.springboot.lookoutside.service.RegionService;

@RestController
@RequestMapping("/region") 
public class RegionController {

	@Autowired 
	private RegionService regionService;
	
	//구,군 리스트 조회 (정확히 원하면 풀 코드)
	@GetMapping("/{regNo}")
	public ResponseDto<List<Region>> regionList(@PathVariable String regNo) {
		List<Region> region = regionService.regionList(regNo);
		return new ResponseDto<List<Region>>(HttpStatus.OK.value(), region);
	}
	
}

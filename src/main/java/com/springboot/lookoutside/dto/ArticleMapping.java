package com.springboot.lookoutside.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

public interface ArticleMapping {

	int getArtNo();
	
	int getUseNo();
	
	int getArtCategory();
	
	String getUseNick();
	
	String getArtSubject();
	
	String getArtContents();
	
	int getArtWselect();
	
	String getRegNo();
	
	String getRegAddr1();

	String getRegAddr2();
	
	@JsonFormat(pattern = "YY.MM.dd HH:mm:ss", timezone = "Asia/Seoul")
	Timestamp getArtCreated();
	
}

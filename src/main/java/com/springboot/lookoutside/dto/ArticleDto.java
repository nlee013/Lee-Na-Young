package com.springboot.lookoutside.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {

	private int artNo;
	
	private int useNo;
	
	private String useNick;
	
	private String artSubject;
	
	private String artContents;
	
	@JsonFormat(pattern = "YY.MM.dd HH:mm:ss", timezone = "Asia/Seoul")
	private Timestamp artCreated;
	
	private int artCategory;

	private String regNo;
	
	private int artWSelect;
	
	private String regAddr1;
	
	private String regAddr2;
	
}

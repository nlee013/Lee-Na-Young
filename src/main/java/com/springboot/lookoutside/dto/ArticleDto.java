package com.springboot.lookoutside.dto;

import java.sql.Timestamp;

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
	
	private String artSubject;
	
	private String artContents;
	
	private Timestamp artCreated;
	
	private int artCategory;

	private String regNo;
	
	private int artWSelect;
	
	private String regAddr1;
	
	private String regAddr2;
	
}

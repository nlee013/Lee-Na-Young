package com.springboot.lookoutside.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.JoinColumn;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Entity
public class ArticleImg {
	
	@JoinColumn(nullable = false)
	private int artNo;
	
	@Column(nullable = false)
	private String imgOrigin;
	
	@Id
	@Column(nullable = false)
	private String imgSave;
	
	@Column(nullable = false)
	private int imgNo;
	
	private String imgPath;
	
}

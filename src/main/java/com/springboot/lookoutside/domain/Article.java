package com.springboot.lookoutside.domain;

import java.sql.Timestamp;
//import java.security.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.DynamicInsert;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@DynamicInsert
public class Article{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int artNo;
	
	@JoinColumn(nullable = false)
	private int useNo;
	
	//@Column(nullable = false)
	//private String artTitle;
	
	@Column(nullable = false)
	private String artSubject;
	
	@Column(nullable = false)
	private String artContents;
	
	@JsonFormat(pattern = "YY.MM.dd HH:mm:ss", timezone = "Asia/Seoul")
	@CreationTimestamp
	@Column(updatable = false)
	private Timestamp artCreated;
	//@UpdateTimestamp
	//private LocalDateTime art_updated;
	
	@Column(nullable = false)
	private int artCategory;
	
	@JoinColumn(nullable = false)
	private int regNo;
	
	@Column(nullable = false)
	private int artWSelect;
	
	//댓글 연관관계
//	@OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
//	private List<ArticleReply> articleReply;
	
	//private String art_heart; //후순위

}

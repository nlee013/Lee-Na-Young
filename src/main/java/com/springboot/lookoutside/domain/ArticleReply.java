package com.springboot.lookoutside.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
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
@Table
@DynamicInsert
@Entity
public class ArticleReply {
	
	@JoinColumn(nullable = false)
	private int artNo; 
	
	@JoinColumn(nullable = false)
	private int useNo; 
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int repNo; 
	
	@Column(nullable = false)
	private String repContents; 

	@JsonFormat(pattern = "YY.MM.dd HH:mm:ss", timezone = "Asia/Seoul")
	@CreationTimestamp
	@Column(updatable = false)
	private Timestamp repCreated;
	

}

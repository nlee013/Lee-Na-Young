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
import javax.persistence.ManyToOne;
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
	
	@Column(nullable = false)
	private int useNo;
	
	@Column(nullable = false)
	private String artSubject;
	
	@Column(nullable = false)
	private String artContents;
	
	@JsonFormat(pattern = "YY.MM.dd HH:mm:ss", timezone = "Asia/Seoul")
	@CreationTimestamp
	@Column(updatable = false)
	private Timestamp artCreated;
	
	@Column(nullable = false)
	private int artCategory;
	
	@Column(nullable = false)
	private int artWSelect;

	@Column(nullable = false)
	private String regNo;
	
}

package com.springboot.lookoutside.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder 
@Entity 
@DynamicInsert 
public class User {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int useNo; 
	
	@Column(nullable = false , unique = true)
	private String useNick;
	
	@Column(nullable = false , unique = true)
	private String useId;
	
	@Column(nullable = false)
	private String usePw;
	
	@Column(nullable = false)
	private String useName;
	
	@Column(nullable = false)
	private Integer useGender;
	
	@ColumnDefault("'USER'")  
	private String useRole; 
	
	@Column(nullable = false)
	private String useEmail;
	
	@JsonFormat(pattern = "YY.MM.dd HH:mm:ss", timezone = "Asia/Seoul")
	@CreationTimestamp 
	private Timestamp useCreated;
	
	private String provider;
	private String providerId;
}

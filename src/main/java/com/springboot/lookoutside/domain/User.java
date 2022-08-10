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
@Builder // 빌더 패턴 //ORM -> Java Object -> Table 매핑
@Entity // User 클래스가 Mysql 테이블 생성
@DynamicInsert // insert시에 null인 필드를 제외시켜준다.
public class User {

	@Id //Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 넘버링 auto-increment
	private int useNo; // 회원번호
	
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
	
	@ColumnDefault("'USER'")  //" " 안에 ' ' 넣어줘야한다. 글자시 "'user'"
	private String useRole; //Enum을 쓰는게 좋다 (domain) //admin,user,manager
	
	@Column(nullable = false)
	private String useEmail;
	
	@JsonFormat(pattern = "YY.MM.dd HH:mm:ss", timezone = "Asia/Seoul")
	@CreationTimestamp // 시간 자동 입력, postman에 따로 key,value 안줘도 자동입력
	private Timestamp useCreated;
	
}

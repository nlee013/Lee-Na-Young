package com.springboot.lookoutside.domain;

//import java.security.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Entity
@Table(name = "article")
public class Article {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int art_no;
	@Column
	private int use_no;
	@Column
	private String art_title;
	@Column
	private String use_nick;
	@Column
	private String art_contents;
	
	@CreationTimestamp
	private LocalDateTime art_created;
	//@UpdateTimestamp
	//private LocalDateTime art_updated;
	
	@Column
	private String art_category;
	@Column
	private String art_addr1;
	@Column
	private String art_addr2;
	@Column
	private String art_wSelect;
	//private String art_heart; //ÈÄ¼øÀ§

}

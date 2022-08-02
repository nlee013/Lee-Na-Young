package com.springboot.lookoutside.domain;

//import java.security.Timestamp;
//import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class ArticleReply {
	
	@Column
	private int art_no;
	@Column
	private int use_no;	
	@Id
	private int rep_no;
	
	//@CreationTimestamp
	//private LocalDateTime rep_created;
	//@UpdateTimestamp
	//private LocalDateTime rep_updated;
	
}

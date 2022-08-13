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
	private int artNo; //게시물 번호
	
	@JoinColumn(nullable = false)
	private int useNo; //회원 번호
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int repNo; //댓글 번호
	
	@Column(nullable = false)
	private String repContents; //댓글 내용

	@JsonFormat(pattern = "YY.MM.dd HH:mm:ss", timezone = "Asia/Seoul")
	@CreationTimestamp
	@Column(updatable = false)
	private Timestamp repCreated;//댓글 등록 날짜, 시간
	
	//private int repCnt;//댓글 갯수
	//private int repGroup;//댓글을 달았을 때(대댓글 이상이 아닌 첫 댓글) 증가하는 값
	//commentGroup은 대댓글 이상 달았을 때 증가하지 않으며,
	//대댓글 혹은 그 이상은 최상위 댓글의 commentGroup와 같은 값을 가진다. 또한 댓글을 정렬한다. 
	
	//@ColumnDefault("0")
	//private int repSequence;
	//대댓글 이상을 작성했을 때 값이 증가하며, 이것으로 계층형 댓글을 정렬한다.
	
	//@ColumnDefault("0")
	//private int repLevel;//계층형 댓글의 깊이
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "artNoP")
//	private Article article;

	
//	public void changeGroup(int repGroup) {
//		this.repGroup = repGroup + 1;
//		
//	}

//	public ArticleReply(Article article, String repContents) {
//		
//		this.article = article;
//		this.repContents = repContents;
//		this.setRepCreated(repCreated);
//	}
	
}

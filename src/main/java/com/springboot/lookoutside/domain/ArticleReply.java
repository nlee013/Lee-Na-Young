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
	private int artNo; //�Խù� ��ȣ
	
	@JoinColumn(nullable = false)
	private int useNo; //ȸ�� ��ȣ
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int repNo; //��� ��ȣ
	
	@Column(nullable = false)
	private String repContents; //��� ����

	@JsonFormat(pattern = "YY.MM.dd HH:mm:ss", timezone = "Asia/Seoul")
	@CreationTimestamp
	@Column(updatable = false)
	private Timestamp repCreated;//��� ��� ��¥, �ð�
	
	//private int repCnt;//��� ����
	//private int repGroup;//����� �޾��� ��(���� �̻��� �ƴ� ù ���) �����ϴ� ��
	//commentGroup�� ���� �̻� �޾��� �� �������� ������,
	//���� Ȥ�� �� �̻��� �ֻ��� ����� commentGroup�� ���� ���� ������. ���� ����� �����Ѵ�. 
	
	//@ColumnDefault("0")
	//private int repSequence;
	//���� �̻��� �ۼ����� �� ���� �����ϸ�, �̰����� ������ ����� �����Ѵ�.
	
	//@ColumnDefault("0")
	//private int repLevel;//������ ����� ����
	
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

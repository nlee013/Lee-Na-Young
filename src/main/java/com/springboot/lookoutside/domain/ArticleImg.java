package com.springboot.lookoutside.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Entity
public class ArticleImg {
	
	@Column(nullable = false)
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

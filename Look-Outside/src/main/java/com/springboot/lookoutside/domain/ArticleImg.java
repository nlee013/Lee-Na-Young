package com.springboot.lookoutside.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

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
public class ArticleImg {
	
	private int art_no;
	private String img_origin;
	private String img_save;
	@Id
	private String img_no;
	
}

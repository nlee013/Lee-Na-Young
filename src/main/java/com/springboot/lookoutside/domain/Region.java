package com.springboot.lookoutside.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.DynamicInsert;

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
public class Region {

	@Id
	private String regNo; 
	
	@Column
	private String regAddr1;
	
	@Column
	private String regAddr2;
	
	
}

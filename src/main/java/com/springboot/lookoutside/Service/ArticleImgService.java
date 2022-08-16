package com.springboot.lookoutside.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.lookoutside.domain.Article;
import com.springboot.lookoutside.domain.ArticleImg;
import com.springboot.lookoutside.repository.ArticleImgRepository;
import com.springboot.lookoutside.repository.ArticleRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ArticleImgService {
	
	@Autowired
	private ArticleImgRepository articleImgRepository;
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@Transactional
	public String saveImg(MultipartFile file) {
		
		ArticleImg articleImg = new ArticleImg();
		
		String uploadPath  = "D:\\images";
		
		String imgOriName = file.getOriginalFilename(); //filename.jpg	
		
		String saveImgName = (new Date().getTime()) + "" + (file.getOriginalFilename()); // 현재 날짜와 랜덤 정수값으로 새로운 파일명 만들기
		
		String fileExtension = imgOriName.substring(imgOriName.lastIndexOf(".") + 1); // ex) jpg
		imgOriName = imgOriName.substring(0, imgOriName.lastIndexOf(".")); // ex) 파일
		//long fileSize = file.getSize(); // 파일 사이즈
		String filePath = uploadPath + "\\" + saveImgName;
		
		
		File fileSave = new File(uploadPath, saveImgName);
		
		if(!fileSave.exists()) { 
			fileSave.mkdirs();
		}
		
		try {
			file.transferTo(fileSave);
		} catch (IllegalStateException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		articleImg = new ArticleImg();
		
		articleImg.setImgSave(saveImgName);
		articleImg.setImgOrigin(imgOriName);
		articleImg.setImgPath(filePath);	
		
		int artNo = articleRepository.findArtNo().orElseThrow(() -> {
			
			return new IllegalArgumentException("0");
		 });
		
		articleImg.setArtNo(artNo);
		
		int imgNo = articleImgRepository.findImgNo(artNo);
		System.out.println(imgNo);
		articleImg.setImgNo(imgNo + 1);	
		
		articleImgRepository.save(articleImg);
		
		return "1";
	}
	
	//이미지 파일 삭제
	@Transactional
	public String deleteImgPost(int artNo) {

		articleImgRepository.deleteByArtNo(artNo);

		return "1";
	}
	
	
}

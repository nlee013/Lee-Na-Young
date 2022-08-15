package com.springboot.lookoutside.service;

import java.util.Optional;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.lookoutside.domain.Article;
import com.springboot.lookoutside.domain.ArticleImg;
import com.springboot.lookoutside.repository.ArticleImgRepository;
import com.springboot.lookoutside.repository.ArticleReplyRepository;
import com.springboot.lookoutside.repository.ArticleRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ArticleService {

	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private ArticleReplyRepository articleReplyRepository;
	
	@Autowired
	private ArticleImgRepository articleImgRepository;
	
	//����¡
	private static final int BlockPageNumCount = 5;
	private static final int PageCount = 12;
	
	//�Խù� ���
	@Transactional
	public Page<Article> getArticleList(Pageable pageable){
		
		Page<Article> articlePage = articleRepository.findAll(pageable);

		return articlePage;
		
	}
	
	//�Խù� ����
	@Transactional
	public long getArticleCount() {
		return articleRepository.count();
	}
	
	//�Խù� ����¡
	public Integer[] getPageList(Integer currentPageNum) {
		Integer[] pageList = new Integer[BlockPageNumCount];
		
		//�� �Խù� ����
		Double totalCount = Double.valueOf(this.getArticleCount());
		
		//�� �Խù� �������� ����� ������ ������ ��ȣ ���(�ø� ���)
		Integer totalLastPageNum = (int)(Math.ceil(totalCount/PageCount));
		
		//���� �������� �������� ���� ������ ������ ��ȣ ���
		Integer blockLastPageNum = (totalLastPageNum > currentPageNum + BlockPageNumCount) ? currentPageNum + BlockPageNumCount : totalLastPageNum;
		
		//������ ���� ��ȣ ����
		currentPageNum = (currentPageNum <= 3)? 1: currentPageNum - 2;
		
		//������ ��ȣ �Ҵ�
		for(int val = currentPageNum, idx = 0; val <= blockLastPageNum; val++, idx++) {
			pageList[idx] = val;
		}
		
		return pageList;
	}

	//게시물 등록
	@Transactional
	public String savePost(String articles) {
		
		
		Article article = new Article() ;
		try {
			article = new ObjectMapper().readValue(articles, Article.class);
		} catch (JsonMappingException e) {
			
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			
			e.printStackTrace();
		}
		System.out.println(article.getArtContents());
		
		articleRepository.save(article);
		
		return "게시물 등록 완료";
		
	}
	
	//게시물 수정
	@Transactional
	public String updatePost(int artNo, String articles) throws JsonMappingException, JsonProcessingException {
		
		Article article = new ObjectMapper().readValue(articles, Article.class);

		Article update = articleRepository.findByArtNo(artNo).orElseThrow(() -> {

			return new IllegalArgumentException("게시물이 존재하지 않습니다.");
		});

		update.setArtSubject(article.getArtSubject());
		update.setArtContents(article.getArtContents());
		update.setArtCategory(article.getArtCategory());
		update.setArtWSelect(article.getArtWSelect());
		update.setRegNo(article.getRegNo());

		return "게시물 수정 완료";
		
	}
	
	//게시물 삭제
	@Transactional
	public String deletePost(int artNo) {
		
		articleRepository.findByArtNo(artNo).orElseThrow(() -> {
		
			return new IllegalArgumentException("게시물 삭제를 실패하였습니다.");
		});
		
		articleRepository.deleteById(artNo);
		return "게시물 삭제 완료";
	}
	

	//검색 기능
	@Transactional
	public Page<Article> searchPosts(Pageable pageable, String keyword, Optional<Integer> artCategory) {

		Page<Article> articleEntities = articleRepository.findByArtSubjectContainingAndArtCategory(pageable, keyword, artCategory);

		return articleEntities;
	}
	

	//게시물 상세 페이지
	public Optional<Article> detailPost(int artNo) {
		
		return articleRepository.findByArtNo(artNo);
		
	}

	@Transactional
	public Article findOne(int artNo) {
		
		return null;
	}

}

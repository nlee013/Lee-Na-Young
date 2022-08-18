package com.springboot.lookoutside.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.lookoutside.domain.Article;
import com.springboot.lookoutside.domain.ArticleImg;
import com.springboot.lookoutside.domain.ArticleReply;
import com.springboot.lookoutside.domain.Region;
import com.springboot.lookoutside.domain.User;
import com.springboot.lookoutside.dto.ArticleMapping;
import com.springboot.lookoutside.repository.ArticleImgRepository;
import com.springboot.lookoutside.repository.ArticleReplyRepository;
import com.springboot.lookoutside.repository.ArticleRepository;
import com.springboot.lookoutside.repository.RegionRepository;
import com.springboot.lookoutside.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ArticleService {

	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private ArticleImgRepository articleImgRepository;

	@Autowired
	private ArticleReplyRepository articleReplyRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RegionRepository regionRepository;
	
	//게시물 목록
	@Transactional
	public Map<String, Object> articleList(int useNo, Pageable pageable){

		Page<ArticleMapping> articlePage = articleRepository.findAllBy(useNo, pageable);
		
		int numberOfElements = articlePage.getNumberOfElements();
		long totalElements = articlePage.getTotalElements();
		int number = articlePage.getNumber();
		int totalPages = articlePage.getTotalPages();
		int size = articlePage.getSize();
		
		Map<String, Object> pageAble = new HashMap<String, Object>();
		
		pageAble.put("numberOfElements", numberOfElements);
		pageAble.put("totalElements", totalElements);
		pageAble.put("number", number);
		pageAble.put("totalPages", totalPages);
		pageAble.put("size", size);
		pageAble.put("offset", articlePage.getPageable().getOffset());
		
		Map<String, Object> article = new HashMap<String, Object>();
		
		article.put("list", articlePage.getContent());
		article.put("pageable", pageAble);
		
		return article;

	}

	//게시물 등록
	@Transactional
	public int savePost(String articles) {

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

		int artNo = articleRepository.save(article).getArtNo();
		
		return artNo;

	}

	//게시물 수정
	@Transactional
	public String updatePost(int artNo, String articles) throws JsonMappingException, JsonProcessingException {

		Article article = new ObjectMapper().readValue(articles, Article.class);

		Article update = articleRepository.findByArtNo(artNo).orElseThrow(() -> {

			return new IllegalArgumentException("0");
		});

		update.setArtSubject(article.getArtSubject());
		update.setArtContents(article.getArtContents());
		update.setArtCategory(article.getArtCategory());
		update.setArtWSelect(article.getArtWSelect());
		update.setRegNo(article.getRegNo());

		return "1";

	}

	//게시물 삭제
	@Transactional
	public String deletePost(int artNo) {

		articleRepository.findByArtNo(artNo).orElseThrow(() -> {

			return new IllegalArgumentException("0");
		});

		articleRepository.deleteById(artNo);
		return "1";
	}

	//검색 기능
	@Transactional
	public Page<Article> searchPosts(Pageable pageable, String keyword, Optional<Integer> artCategory) {

		Page<Article> articleEntities = articleRepository.findByArtSubjectContainingAndArtCategory(pageable, keyword, artCategory);

		return articleEntities;
	}


	//게시물 상세 페이지
	public Map<String, Object> detailPost(int artNo) {

		Article article = articleRepository.findByArtNo(artNo).orElseThrow(() -> {

			return new IllegalArgumentException("0");
			
		});
		
		String regNo = article.getRegNo();
		
		User user = userRepository.findByUseNo2(article.getUseNo());
		
		List<ArticleImg> articleImg = articleImgRepository.findAllByArtNo(artNo);
		
		List<ArticleReply> articleReply = articleReplyRepository.findAllByArtNo(artNo);
		
		Region region = regionRepository.findByRegNo(regNo);
		
		Map<String, Object> detail = new HashMap<String, Object>();
		
		detail.put("article", article);
		detail.put("region", region);
		detail.put("articleImg", articleImg);
		detail.put("articleReply", articleReply);
		
		//System.out.println(articleRepository.findAllByRegNoQuery(regNo));

		return detail;

	}
	
	//카테고리, 지역별 게시물 목록 조회
	@Transactional
	public Map<String, Object> articleListCateRegNo(int artCategory, String regNo, Pageable pageable){

		Page<ArticleMapping> articlePage = articleRepository.findAllByArtCategoryAndRegNoStartingWith(artCategory, regNo, pageable);

		int numberOfElements = articlePage.getNumberOfElements();
		long totalElements = articlePage.getTotalElements();
		int number = articlePage.getNumber();
		int totalPages = articlePage.getTotalPages();
		int size = articlePage.getSize();
		
		Map<String, Object> pageAble = new HashMap<String, Object>();
		
		pageAble.put("numberOfElements", numberOfElements);
		pageAble.put("totalElements", totalElements);
		pageAble.put("number", number);
		pageAble.put("totalPages", totalPages);
		pageAble.put("size", size);
		pageAble.put("offset", articlePage.getPageable().getOffset());
		
		Map<String, Object> article = new HashMap<String, Object>();
		
		article.put("list", articlePage.getContent());
		article.put("pageable", pageAble);
		
		return article;
	}
	

}

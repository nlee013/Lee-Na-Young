package com.springboot.lookoutside.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Vector;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.lookoutside.domain.Article;
import com.springboot.lookoutside.domain.ArticleImg;
import com.springboot.lookoutside.domain.ArticleReply;
import com.springboot.lookoutside.domain.Region;
import com.springboot.lookoutside.dto.ArticleDto;
import com.springboot.lookoutside.repository.ArticleImgRepository;
import com.springboot.lookoutside.repository.ArticleReplyRepository;
import com.springboot.lookoutside.repository.ArticleRepository;
import com.springboot.lookoutside.repository.RegionRepository;

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
	private RegionRepository regionRepository;

	//페이징
	private static final int BlockPageNumCount = 5;
	private static final int PageCount = 12;

	//게시물 목록
	@Transactional
	public Page<ArticleDto> articleList(int useNo, Pageable pageable){

		Page<ArticleDto> articlePage = articleRepository.findAllArticleDto(useNo, pageable);

		return articlePage;

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

		return "1";

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
		
		List<ArticleImg> articleImg = articleImgRepository.findAllByArtNo(artNo);
		
		List<ArticleReply> articleReply = articleReplyRepository.findAllByArtNo(artNo);
		
		List<Region> region = regionRepository.findByRegNoStartingWith(regNo);
		
		Map<String, Object> detail = new HashMap<String, Object>();
		
		detail.put("article", article);
		detail.put("articleImg", articleImg);
		detail.put("articleReply", articleReply);
		detail.put("region", region);

		return detail;

	}
	
	//카테고리, 지역별 게시물 목록 조회
	@Transactional
	public Page<Article> articleListCateRegNo(int artCategory, String regNo, Pageable pageable){

		Page<Article> articlePage = articleRepository.findAllByArtCategoryAndRegNoStartingWith(artCategory, regNo, pageable);

		return articlePage;
	}
	
	//	
	//	@Transactional
	//	public int saveReply(ArticleReplyDTO articleReplyDTO) {
	//		
	//		return articleReplyRepository.save(articleReplyDTO);
	//	}


}

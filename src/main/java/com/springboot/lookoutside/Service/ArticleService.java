package com.springboot.lookoutside.service;

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

	//페이징
	private static final int BlockPageNumCount = 5;
	private static final int PageCount = 12;

	//게시물 목록
	@Transactional
	public Page<Article> getArticleList(Pageable pageable){

		Page<Article> articlePage = articleRepository.findAll(pageable);

		return articlePage;

		/*
	//목록
	@Transactional
	public List<ArticleDTO> getArticleList(Integer pageNum){

		Page<Article> page = articleRepository.findAll(PageRequest.of(pageNum - 1, PageCount, Sort.by(Sort.Direction.ASC, "artCreated")));

		List<Article> articleEntities = articleRepository.findAll();
		List<ArticleDTO> articleDTOList = new ArrayList<>();

		for(Article article : page) {
			ArticleDTO articleDTO = ArticleDTO.builder()
					.useNo(article.getUseNo())
					.artNo(article.getArtNo())
					.artSubject(article.getArtSubject())
					.artContents(article.getArtContents())
					.artCategory(article.getArtCategory())
					.artWSelect(article.getArtWSelect())
					.artAddr1(article.getArtAddr1())
					.artAddr2(article.getArtAddr2())
					.artCreated(article.getArtCreated())
					.build();

			articleDTOList.add(articleDTO);
//			articleDTOList.add(this.convertEntityToDTO(article));

		}
		System.out.println(articleDTOList);
		return articleDTOList;
		 */

	}

	//게시물 갯수
	@Transactional
	public long getArticleCount() {
		return articleRepository.count();
	}

	//게시물 페이징
	public Integer[] getPageList(Integer currentPageNum) {
		Integer[] pageList = new Integer[BlockPageNumCount];

		//총 게시물 갯수
		Double totalCount = Double.valueOf(this.getArticleCount());

		//총 게시물 기준으로 계산한 마지막 페이지 번호 계산(올림 계산)
		Integer totalLastPageNum = (int)(Math.ceil(totalCount/PageCount));

		//현재 페이지를 기준으로 블럭의 마지막 페이지 번호 계산
		Integer blockLastPageNum = (totalLastPageNum > currentPageNum + BlockPageNumCount) ? currentPageNum + BlockPageNumCount : totalLastPageNum;

		//페이지 시작 번호 조정
		currentPageNum = (currentPageNum <= 3)? 1: currentPageNum - 2;

		//페이지 번호 할당
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

			return new IllegalArgumentException("게시물이 존재하지 않습니다.");
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
	public String detailPost(Article article, int artNo) {

		articleRepository.findByArtNo(artNo).orElseThrow(() -> {

			return new IllegalArgumentException("해당 게시물의 상세 페이지를 찾을 수 없습니다.");
		});

		articleRepository.findByArtNo(artNo);

		return "게시물 상세페이지";

	}
	//	
	//	@Transactional
	//	public int saveReply(ArticleReplyDTO articleReplyDTO) {
	//		
	//		return articleReplyRepository.save(articleReplyDTO);
	//	}


}
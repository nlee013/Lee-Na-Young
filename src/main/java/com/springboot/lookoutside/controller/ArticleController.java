package com.springboot.lookoutside.controller;

import java.util.Optional;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.springboot.lookoutside.domain.Article;
import com.springboot.lookoutside.domain.ArticleImg;
import com.springboot.lookoutside.domain.ArticleReply;
import com.springboot.lookoutside.dto.ResponseDTO;
import com.springboot.lookoutside.service.ArticleImgService;
import com.springboot.lookoutside.service.ArticleReplyService;
import com.springboot.lookoutside.service.ArticleService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/article")
@AllArgsConstructor
public class ArticleController {
	
	@Autowired
	@Resource
	private ArticleService articleService;
	
	@Autowired
	@Resource
	private ArticleReplyService articleReplyService;
	
	@Autowired
	@Resource
	private ArticleImgService articleImgService;
	
	//페이징
	//올린 게시물 목록 페이지
	@GetMapping("/list/{useNo}")
	public ResponseDTO<Page<Article>> list(@PageableDefault(size=3, sort="artNo", direction = Sort.Direction.DESC) Pageable pageable){
		Page<Article> articleList = articleService.getArticleList(pageable);
		return new ResponseDTO<Page<Article>>(HttpStatus.OK.value(), articleList);
	}
	
	//게시물 작성 페이지
	@GetMapping("/post")
	public String upload() {
		System.out.println("글 작성 페이지");
		return "article/upload.html";
	}
	 
	//게시물 작성 + 이미지 파일 첨부
	@PostMapping("/post")
	public ResponseDTO<String> upload(MultipartFile[] multipartFiles, String articles) throws Exception{
		
		System.out.println(articles);
		
		String save = articleService.savePost(articles);//이미지 파일 제외 데이터 저장
		
		System.out.println(multipartFiles);
		
		for(int i = 0; i < multipartFiles.length; i++) {
			
			MultipartFile file = multipartFiles[i];
			
			articleImgService.saveImg(file);//이미지 파일 저장
			
			System.out.println("게시물 올리기");
		}	
		
		System.out.println(multipartFiles);		
		return new ResponseDTO<String>(HttpStatus.OK.value(), save);
		
	}
	
	//게시물 수정 페이지
	@PutMapping("/{artNo}")
	public ResponseDTO<String> update(@PathVariable int artNo, MultipartFile[] multipartFiles, String articles) throws JsonMappingException, JsonProcessingException {

		String update = articleService.updatePost(artNo,articles);

		articleImgService.deleteImgPost(artNo);//이미지 파일 삭제

		System.out.println(multipartFiles);
		System.out.println(artNo);
		System.out.println("게시물 수정 전");
		
		for(int i = 0; i < multipartFiles.length; i++) {

			MultipartFile file = multipartFiles[i];

			articleImgService.saveImg(file);//이미지 파일 저장\
			System.out.println("게시물 수정하기");
		}	

		System.out.println(multipartFiles);
		return new ResponseDTO<String>(HttpStatus.OK.value(), update);
	}

		
	//게시물 상세 조회 페이지
	@GetMapping("/{artNo}")
	public ResponseDTO<Optional<Article>> detail(@PathVariable int artNo) {
		
		Optional<Article> detail = articleService.detailPost(artNo);
		System.out.println("게시물 상세 페이지입니다.");
		
		return new ResponseDTO<Optional<Article>>(HttpStatus.OK.value(), detail);
	}
	
	//삭제
	@DeleteMapping("/{artNo}")
	public ResponseDTO<String> delete(@PathVariable("artNo") int artNo) {
		
		String deleteArt = articleService.deletePost(artNo);
		System.out.println("게시물 삭제하기");
		return new ResponseDTO<String>(HttpStatus.OK.value(), deleteArt);
	}
	
	
	//검색
	@GetMapping("/search")
	public ResponseDTO<Page<Article>> search(@PageableDefault(size=3, sort="artNo", direction = Sort.Direction.DESC) Pageable pageable, String keyword, Optional<Integer> artCategory) {
		Page<Article> articleList = articleService.searchPosts(pageable, keyword, artCategory);
		return new ResponseDTO<Page<Article>>(HttpStatus.OK.value(), articleList);

	}

	//댓글 등록
	//@PostMapping("/{artNo}/reply")
	//public ResponseDTO<Integer> articleReplySave (@RequestBody ArticleReplyDTO articleReplyDTO) {
			
		//int saveReply = articleService.saveReply(articleReplyDTO);
		//System.out.println("댓글 쓰기");
			
		//articleReplyService.replySave(findArticle, articleReplyDTO.getRepContents());
			
		//return new ResponseDTO<Integer>(HttpStatus.OK.value(), saveReply);
			
	//}
		
	//댓글 수정
	@PutMapping("/{artNo}/reply/{replyNo}")
	public ResponseDTO<ArticleReply> articleReplyUpdate (@PathVariable int replyNo) {
				
		ArticleReply replyUpdate = articleReplyService.replyUpdate(replyNo);
		System.out.println("댓글 수정하기");
		
		return new ResponseDTO<ArticleReply>(HttpStatus.OK.value(), replyUpdate);
				
	}
			
	//댓글 삭제
	@DeleteMapping("/{artNo}/reply/{replyNo}")
	public ResponseDTO<ArticleReply> articleReplyDelete (@PathVariable int replyNo) {
				
		ArticleReply replyDelete = articleReplyService.replyDelete(replyNo);
		System.out.println("댓글 삭제하기");
		
		return new ResponseDTO<ArticleReply>(HttpStatus.OK.value(), replyDelete);
				
	}
		
//	//댓글 목록
//	@GetMapping("/{artNo}/reply/{replyNo}")
//	public ResponseDTO<List<ArticleReply>> articleReplyList (@PathVariable int replyNo, @RequestBody ArticleReplyDTO articleReplyDTO) {
//					
//		ArticleReply replyList = articleReplyService.replyList(replyNo);
//		System.out.println("댓글 목록 불러오기");
//		
//		return new ResponseDTO<List<ArticleReply>>(HttpStatus.OK.value(), replyList);
//					
//	}
	
}

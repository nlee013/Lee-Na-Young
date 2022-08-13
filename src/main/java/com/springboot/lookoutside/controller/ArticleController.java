package com.springboot.lookoutside.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;

import com.springboot.lookoutside.domain.Article;
import com.springboot.lookoutside.domain.ArticleReply;
import com.springboot.lookoutside.dto.ResponseDto;
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
	
	//페이징
	//올린 게시물 목록 페이지
	@GetMapping("/list/{useNo}")
	public ResponseDto<Page<Article>> list(@PageableDefault(size=5, sort="artNo", direction = Sort.Direction.DESC) Pageable pageable){
		Page<Article> articleList = articleService.getArticleList(pageable);
		return new ResponseDto<Page<Article>>(HttpStatus.OK.value(), articleList);
	}
	
	/*
	@GetMapping("/list/{pageNum}")
	public ResponseDTO<List<ArticleDTO>> list(@RequestParam(value="page", defaultValue = "1") Integer pageNum) {
		
		List<ArticleDTO> articleList = articleService.getArticleList(pageNum);
		Integer[] pageList = articleService.getPageList(pageNum);
		
		//model.addAttribute("articleList", articleList);
		
		System.out.println("Article Controller : 게시물 목록 출력");
		return new ResponseDTO<List<ArticleDTO>>(HttpStatus.OK.value(), articleList);
	}
	*/
	
	//게시물 작성 페이지
	@GetMapping("/post")
	public String upload() {
		System.out.println("글 작성 페이지");
		return "article/upload.html";
	}
	
	//게시물 올리기 페이지
	@PostMapping("/post")
	public ResponseDto<Integer> upload(@RequestBody Article article) {
		
		articleService.savePost(article);
		System.out.println("게시물 올리기");
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
		
	}
	 
	//게시물 수정 페이지
	@PutMapping("/{artNo}")
	public ResponseDto<String> update(@PathVariable int artNo, @RequestBody Article article) {
		
		String update = articleService.updatePost(article, artNo);
		System.out.println("게시물 수정하기");
		
		return new ResponseDto<String>(HttpStatus.OK.value(), update);
	}
		
	//게시물 상세 조회 페이지
	@GetMapping("/{artNo}")
	public ResponseDto<String> detail(@PathVariable int artNo, Article article) {
		
		String detail = articleService.detailPost(article, artNo);
		System.out.println("게시물 상세 페이지입니다.");
		
		return new ResponseDto<String>(HttpStatus.OK.value(), detail);
	}
	
	//삭제
	@DeleteMapping("/{artNo}")
	public ResponseDto<String> delete(@PathVariable("artNo") int artNo) {
		
		String deleteArt = articleService.deletePost(artNo);
		System.out.println("게시물 삭제하기");
		return new ResponseDto<String>(HttpStatus.OK.value(), deleteArt);
	}
	
	//검색
	@GetMapping("/search")
	public ResponseDto<Page<Article>> search(@PageableDefault(size=3, sort="artNo", direction = Sort.Direction.DESC) Pageable pageable, String keyword, Optional<Integer> artCategory) {
		Page<Article> articleList = articleService.searchPosts(pageable, keyword, artCategory);
		return new ResponseDto<Page<Article>>(HttpStatus.OK.value(), articleList);

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
	public ResponseDto<ArticleReply> articleReplyUpdate (@PathVariable int replyNo) {
				
		ArticleReply replyUpdate = articleReplyService.replyUpdate(replyNo);
		System.out.println("댓글 수정하기");
		
		return new ResponseDto<ArticleReply>(HttpStatus.OK.value(), replyUpdate);
				
	}
			
	//댓글 삭제
	@DeleteMapping("/{artNo}/reply/{replyNo}")
	public ResponseDto<ArticleReply> articleReplyDelete (@PathVariable int replyNo) {
				
		ArticleReply replyDelete = articleReplyService.replyDelete(replyNo);
		System.out.println("댓글 삭제하기");
		
		return new ResponseDto<ArticleReply>(HttpStatus.OK.value(), replyDelete);
				
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

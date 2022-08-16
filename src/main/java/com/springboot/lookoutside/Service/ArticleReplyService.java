package com.springboot.lookoutside.service;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springboot.lookoutside.domain.ArticleReply;
import com.springboot.lookoutside.repository.ArticleReplyRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ArticleReplyService {

	@Autowired
	private ArticleReplyRepository articleReplyRepository;

	//댓긇 등록
	@Transactional
	public String saveReply(ArticleReply articleReply) {
		
		articleReplyRepository.save(articleReply);
		
		return "1";
	}

	//댓글 수정
	public String updateReply(int repNo, ArticleReply articleReply) {
		
		articleReply =  articleReplyRepository.findByRepNo(repNo).orElseThrow(() -> { 
			
			return new IllegalArgumentException("0");
		});
		
		articleReply.setRepContents(articleReply.getRepContents());
		
		return "1";
	}

	//댓글 삭제
	public String deleteReply(int repNo) {
		
		articleReplyRepository.findByRepNo(repNo).orElseThrow(() -> { 
			return new IllegalArgumentException("0");
		});

		articleReplyRepository.deleteById(repNo);
		
		return "1";
	}

	//댓글 목록
	public List<ArticleReply> replyList(int repNo, int artNo, ArticleReply articleReply) {
		
		List<ArticleReply> replyList = articleReplyRepository.findAllByArtNo(artNo);
		
		return replyList;
	}
	
	
}

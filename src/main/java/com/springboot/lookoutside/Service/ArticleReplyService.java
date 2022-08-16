package com.springboot.lookoutside.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.lookoutside.domain.Article;
import com.springboot.lookoutside.domain.ArticleReply;
import com.springboot.lookoutside.repository.ArticleReplyRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ArticleReplyService {

	@Autowired
	private ArticleReplyRepository articleReplyRepository;

	public ArticleReply replyUpdate(int replyNo) {
		
		return null;
	}

	public ArticleReply replyDelete(int replyNo) {
		
		return null;
	}

	public void replySave(Article findArticle, String repContents) {
		// TODO Auto-generated method stub
		
	}

	public ArticleReply replyList(int replyNo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}

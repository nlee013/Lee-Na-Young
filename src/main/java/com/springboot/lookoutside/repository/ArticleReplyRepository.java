package com.springboot.lookoutside.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.lookoutside.domain.ArticleReply;

@Repository
public interface ArticleReplyRepository extends JpaRepository<ArticleReply, Integer> {

	Optional<ArticleReply> findByRepNo(int repNo);
	
	//게시물에 대한 댓글 조회
	List<ArticleReply> findAllByArtNo(int artNo);
}

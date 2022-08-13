package com.springboot.lookoutside.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.lookoutside.domain.ArticleReply;

@Repository
public interface ArticleReplyRepository extends JpaRepository<ArticleReply, Integer> {

	Optional<ArticleReply> findByRepNo(int repNo);
}

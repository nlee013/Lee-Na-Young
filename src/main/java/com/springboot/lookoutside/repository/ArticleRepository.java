package com.springboot.lookoutside.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.springboot.lookoutside.domain.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer>{
	
	 Optional<Article> findByArtNo(int artNo);

    Page<Article> findByArtSubjectContainingAndArtCategory(Pageable pageable,String keyword, Optional<Integer> artCategory);
	Page<Article> findByArtContentsContainingAndArtCategory(Pageable pageable,String keyword, int artCategory);

	Page<Article> findAll(Pageable pageable);
	
	@Query(value = "SELECT artNo FROM article ORDER BY artCreated DESC LIMIT 1", nativeQuery = true)
	Optional<Integer> findArtNo();
	
}

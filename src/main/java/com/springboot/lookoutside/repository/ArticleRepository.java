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

	//cotaining을 붙여주면 Like 검색이 된다 %{keyword}%
	 //Optional<Article> findByTitleContaining(String keyword);
//	Page<Article> findByArtSubjectContaining(Pageable pageable,String keyword);//게시물 제목으로 검색
    Page<Article> findByArtSubjectContainingAndArtCategory(Pageable pageable,String keyword, Optional<Integer> artCategory);
	Page<Article> findByArtContentsContainingAndArtCategory(Pageable pageable,String keyword, int artCategory);

	//내가 쓴 게시물 조회
	Page<Article> findAllByUseNo(int useNo, Pageable pageable);
	
	//카테고리 별 게시물 조회
	Page<Article> findAllByArtCategory(int artCategory, Pageable pageable);
	
	//카테고리, 지역 별 게시물 조회
	Page<Article> findAllByArtCategoryAndRegNoStartingWith(int artCategory, String regNo, Pageable pageable);
	
	//Img테이블에 artNo을 주기 위함
	@Query(value = "SELECT artNo FROM lo.Article ORDER BY artCreated DESC LIMIT 1", nativeQuery = true)
	Optional<Integer> findArtNo();	
}

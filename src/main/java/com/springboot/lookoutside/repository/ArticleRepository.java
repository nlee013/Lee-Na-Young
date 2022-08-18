package com.springboot.lookoutside.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.lookoutside.domain.Article;
import com.springboot.lookoutside.dto.ArticleMapping;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer>{
	
	 Optional<Article> findByArtNo(int artNo);

	//cotaining을 붙여주면 Like 검색이 된다 %{keyword}%
	 //Optional<Article> findByTitleContaining(String keyword);
//	Page<Article> findByArtSubjectContaining(Pageable pageable,String keyword);//게시물 제목으로 검색
    Page<Article> findByArtSubjectContainingAndArtCategory(Pageable pageable,String keyword, Optional<Integer> artCategory);
	Page<Article> findByArtContentsContainingAndArtCategory(Pageable pageable,String keyword, int artCategory);

	//내가 쓴 게시물 조회(마이페이지) -> 삭제예정
	Page<Article> findAllByUseNo(int useNo, Pageable pageable);
	
	//카테고리 별 게시물 목록 조회 (관리자페이지)
	//Page<Article> findAllByArtCategory(int artCategory, Pageable pageable);
	
	//저체 게시물 목록 조회 (관리자페이지)
	@Query(value = "select a.*, u.useNick, r.regAddr1, r.regAddr2, i.imgSave "
			+ "from lo.Article a "
			+ "join lo.Region r on a.regNo = r.regNo "
			+ "join lo.User u on a.useNo = u.useNo "
			+ "join lo.ArticleImg i on a.artNo = i.artNo ",
			countQuery ="select a.*, u.useNick, r.regAddr1, r.regAddr2, i.imgSave "
					+ "from lo.Article a "
					+ "join lo.Region r on a.regNo = r.regNo "
					+ "join lo.User u on a.useNo = u.useNo "
					+ "join lo.ArticleImg i on a.artNo = i.artNo ",
			nativeQuery = true)
	Page<ArticleMapping> findList(Pageable pageable);
	
	//카테고리 별 게시물 목록 조회 (관리자페이지)
	@Query(value = "select a.*, u.useNick, r.regAddr1, r.regAddr2, i.imgSave "
			+ "from lo.Article a "
			+ "join lo.Region r on a.regNo = r.regNo "
			+ "join lo.User u on a.useNo = u.useNo "
			+ "join lo.ArticleImg i on a.artNo = i.artNo "
			+ "where a.artCategory = ?1 ",
			countQuery ="select a.*, u.useNick, r.regAddr1, r.regAddr2, i.imgSave "
					+ "from lo.Article a "
					+ "join lo.Region r on a.regNo = r.regNo "
					+ "join lo.User u on a.useNo = u.useNo "
					+ "join lo.ArticleImg i on a.artNo = i.artNo "
					+ "where a.artCategory = ?1 ",
			nativeQuery = true)
	Page<ArticleMapping> findAllByArtCategory(int artCategory, Pageable pageable);
	
	//카테고리, 지역 별 게시물 조회
	//Page<Article> findAllByArtCategoryAndRegNoStartingWith(int artCategory, String regNo, Pageable pageable);
	@Query(value = "select a.*, u.useNick, r.regAddr1, r.regAddr2, i.imgSave "
			+ "from lo.Article a "
			+ "join lo.Region r on a.regNo = r.regNo "
			+ "join lo.User u on a.useNo = u.useNo "
			+ "join lo.ArticleImg i on a.artNo = i.artNo "
			+ "where a.artCategory = ?1 "
			+ "and a.regNo like ?2%",
			countQuery ="select a.*, u.useNick, r.regAddr1, r.regAddr2, i.imgSave "
					+ "from lo.Article a "
					+ "join lo.Region r on a.regNo = r.regNo "
					+ "join lo.User u on a.useNo = u.useNo "
					+ "join lo.ArticleImg i on a.artNo = i.artNo "
					+ "where a.artCategory = ?1 "
					+ "and a.regNo like ?2%",
			nativeQuery = true)
	Page<ArticleMapping> findAllByArtCategoryAndRegNoStartingWith(int artCategory, String regNo, Pageable pageable);
	
	//내가 쓴 게시물 조회(마이페이지)
	@Query(value = "select a.*, u.useNick, r.regAddr1, r.regAddr2, i.imgSave "
			+ "from lo.Article a "
			+ "join lo.Region r on a.regNo = r.regNo "
			+ "join lo.User u on a.useNo = u.useNo "
			+ "join lo.ArticleImg i on a.artNo = i.artNo "
			+ "where u.useNo = ?1",
			countQuery ="select a.*, u.useNick, r.regAddr1, r.regAddr2, i.imgSave "
					+ "from lo.Article a "
					+ "join lo.Region r on a.regNo = r.regNo "
					+ "join lo.User u on a.useNo = u.useNo "
					+ "join lo.ArticleImg i on a.artNo = i.artNo "
					+ "where u.useNo = ?1",
			nativeQuery = true)
	Page<ArticleMapping> findAllBy(int useNo, Pageable pageable);
	
}

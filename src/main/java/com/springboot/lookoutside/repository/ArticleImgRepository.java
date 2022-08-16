package com.springboot.lookoutside.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.springboot.lookoutside.domain.ArticleImg;

@Repository
public interface ArticleImgRepository extends JpaRepository<ArticleImg, Integer>{
	
	ArticleImg findByImgNo(int imgNo);
	
	@Query(value = "SELECT IFNULL (max(imgNo), 0) FROM lo.ArticleImg WHERE artNo=?1 ORDER BY imgNo DESC LIMIT 1", nativeQuery = true)
	Integer findImgNo(int artNo);
	
	int deleteByArtNo(int artNo);
	
	//게시물에 대한 사진 불러오기
	List<ArticleImg> findAllByArtNo(int artNo);
}

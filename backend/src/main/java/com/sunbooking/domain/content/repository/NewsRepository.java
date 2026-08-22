package com.sunbooking.domain.content.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunbooking.domain.content.entity.News;

public interface NewsRepository extends JpaRepository<News, Long> {
	List<News> findByStatusIgnoreCase(String status);
}
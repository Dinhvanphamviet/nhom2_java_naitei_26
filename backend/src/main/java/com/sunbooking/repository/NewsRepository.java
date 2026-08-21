package com.sunbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunbooking.entity.News;

public interface NewsRepository extends JpaRepository<News, Long> {
}
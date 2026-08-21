package com.sunbooking.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunbooking.dto.content.NewsResponse;
import com.sunbooking.repository.NewsRepository;

@Service
public class NewsService {

    private final NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Transactional(readOnly = true)
    public List<NewsResponse> findAll() {
        return newsRepository.findAll().stream()
                .map(NewsResponse::from)
                .toList();
    }
}
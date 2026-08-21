package com.sunbooking.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunbooking.dto.content.FoodResponse;
import com.sunbooking.dto.content.NewsResponse;
import com.sunbooking.dto.content.PlaceResponse;
import com.sunbooking.service.FoodService;
import com.sunbooking.service.NewsService;
import com.sunbooking.service.PlaceService;

@RestController
@RequestMapping("/api")
public class ContentController {

    private final PlaceService placeService;
    private final FoodService foodService;
    private final NewsService newsService;

    public ContentController(PlaceService placeService, FoodService foodService, NewsService newsService) {
        this.placeService = placeService;
        this.foodService = foodService;
        this.newsService = newsService;
    }

    @GetMapping("/places")
    public List<PlaceResponse> listPlaces() {
        return placeService.findAll();
    }

    @GetMapping("/food")
    public List<FoodResponse> listFood() {
        return foodService.findAll();
    }

    @GetMapping("/news")
    public List<NewsResponse> listNews() {
        return newsService.findAll();
    }
}
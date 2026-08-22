package com.sunbooking.domain.content.controller.admin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sunbooking.domain.content.dto.FoodRequest;
import com.sunbooking.domain.content.dto.FoodResponse;
import com.sunbooking.domain.content.dto.NewsRequest;
import com.sunbooking.domain.content.dto.NewsResponse;
import com.sunbooking.domain.content.dto.PlaceRequest;
import com.sunbooking.domain.content.dto.PlaceResponse;
import com.sunbooking.domain.content.service.FoodService;
import com.sunbooking.domain.content.service.NewsService;
import com.sunbooking.domain.content.service.PlaceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/content")
@PreAuthorize("hasRole('ADMIN')")
public class AdminContentController {

    private final PlaceService placeService;
    private final FoodService foodService;
    private final NewsService newsService;

    public AdminContentController(PlaceService placeService, FoodService foodService, NewsService newsService) {
        this.placeService = placeService;
        this.foodService = foodService;
        this.newsService = newsService;
    }

    @GetMapping("/places")
    public List<PlaceResponse> listPlaces() {
        return placeService.findAllAdmin();
    }

    @GetMapping("/places/{id}")
    public PlaceResponse getPlace(@PathVariable Long id) {
        return placeService.findByIdAdmin(id);
    }

    @PostMapping("/places")
    @ResponseStatus(HttpStatus.CREATED)
    public PlaceResponse createPlace(@Valid @RequestBody PlaceRequest request) {
        return placeService.create(request);
    }

    @PutMapping("/places/{id}")
    public PlaceResponse updatePlace(@PathVariable Long id, @Valid @RequestBody PlaceRequest request) {
        return placeService.update(id, request);
    }

    @DeleteMapping("/places/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlace(@PathVariable Long id) {
        placeService.delete(id);
    }

    @GetMapping("/food")
    public List<FoodResponse> listFood() {
        return foodService.findAllAdmin();
    }

    @GetMapping("/food/{id}")
    public FoodResponse getFood(@PathVariable Long id) {
        return foodService.findByIdAdmin(id);
    }

    @PostMapping("/food")
    @ResponseStatus(HttpStatus.CREATED)
    public FoodResponse createFood(@Valid @RequestBody FoodRequest request) {
        return foodService.create(request);
    }

    @PutMapping("/food/{id}")
    public FoodResponse updateFood(@PathVariable Long id, @Valid @RequestBody FoodRequest request) {
        return foodService.update(id, request);
    }

    @DeleteMapping("/food/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFood(@PathVariable Long id) {
        foodService.delete(id);
    }

    @GetMapping("/news")
    public List<NewsResponse> listNews() {
        return newsService.findAllAdmin();
    }

    @GetMapping("/news/{id}")
    public NewsResponse getNews(@PathVariable Long id) {
        return newsService.findByIdAdmin(id);
    }

    @PostMapping("/news")
    @ResponseStatus(HttpStatus.CREATED)
    public NewsResponse createNews(@Valid @RequestBody NewsRequest request) {
        return newsService.create(request);
    }

    @PutMapping("/news/{id}")
    public NewsResponse updateNews(@PathVariable Long id, @Valid @RequestBody NewsRequest request) {
        return newsService.update(id, request);
    }

    @DeleteMapping("/news/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNews(@PathVariable Long id) {
        newsService.delete(id);
    }
}

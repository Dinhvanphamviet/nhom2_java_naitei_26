package com.sunbooking.controller;

import com.sunbooking.dto.tour.TourResponse;
import com.sunbooking.service.TourService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tours")
public class TourController {

    private final TourService tourService;

    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @GetMapping
    public List<TourResponse> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId) {
        return tourService.search(keyword, categoryId);
    }

    @GetMapping("/{id}")
    public TourResponse get(@PathVariable Long id) {
        return tourService.findById(id);
    }
}
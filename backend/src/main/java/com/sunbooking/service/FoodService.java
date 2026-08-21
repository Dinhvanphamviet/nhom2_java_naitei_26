package com.sunbooking.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunbooking.dto.content.FoodResponse;
import com.sunbooking.repository.FoodRepository;

@Service
public class FoodService {

    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Transactional(readOnly = true)
    public List<FoodResponse> findAll() {
        return foodRepository.findAll().stream()
                .map(FoodResponse::from)
                .toList();
    }
}
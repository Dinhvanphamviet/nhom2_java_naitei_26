package com.sunbooking.domain.content.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunbooking.domain.content.dto.FoodRequest;
import com.sunbooking.domain.content.dto.FoodResponse;
import com.sunbooking.domain.content.entity.Food;
import com.sunbooking.domain.content.repository.FoodRepository;
import com.sunbooking.domain.content.repository.PlaceRepository;
import com.sunbooking.global.exception.ResourceNotFoundException;

@Service
public class FoodService {

    private final FoodRepository foodRepository;
    private final PlaceRepository placeRepository;

    public FoodService(FoodRepository foodRepository, PlaceRepository placeRepository) {
        this.foodRepository = foodRepository;
        this.placeRepository = placeRepository;
    }

    @Transactional(readOnly = true)
    public List<FoodResponse> findAll() {
        return foodRepository.findByStatusIgnoreCase("ACTIVE").stream()
                .map(FoodResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public FoodResponse findById(Long id) {
        return FoodResponse.from(findActiveFood(id));
    }

    @Transactional(readOnly = true)
    public List<FoodResponse> findAllAdmin() {
        return foodRepository.findAll().stream()
                .map(FoodResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public FoodResponse findByIdAdmin(Long id) {
        return FoodResponse.from(findFood(id));
    }

    @Transactional
    public FoodResponse create(FoodRequest request) {
        Food food = new Food();
        apply(food, request);
        return FoodResponse.from(foodRepository.save(food));
    }

    @Transactional
    public FoodResponse update(Long id, FoodRequest request) {
        Food food = findFood(id);
        apply(food, request);
        return FoodResponse.from(foodRepository.save(food));
    }

    @Transactional
    public void delete(Long id) {
        Food food = findFood(id);
        food.setStatus("INACTIVE");
        foodRepository.save(food);
    }

    private Food findFood(Long id) {
        return foodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food not found: " + id));
    }

    private Food findActiveFood(Long id) {
        Food food = findFood(id);
        if (!"ACTIVE".equalsIgnoreCase(food.getStatus())) {
            throw new ResourceNotFoundException("Food not found: " + id);
        }
        return food;
    }

    private void apply(Food food, FoodRequest request) {
        if (request.placeId() != null && !placeRepository.existsById(request.placeId())) {
            throw new ResourceNotFoundException("Place not found: " + request.placeId());
        }
        food.setPlaceId(request.placeId());
        food.setName(request.name().trim());
        food.setDescription(request.description());
        food.setPrice(request.price());
        food.setImageUrl(request.imageUrl());
        food.setStatus(request.status());
    }
}
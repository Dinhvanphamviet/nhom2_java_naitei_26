package com.sunbooking.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunbooking.dto.content.PlaceResponse;
import com.sunbooking.repository.PlaceRepository;

@Service
public class PlaceService {

    private final PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @Transactional(readOnly = true)
    public List<PlaceResponse> findAll() {
        return placeRepository.findAll().stream()
                .map(PlaceResponse::from)
                .toList();
    }
}
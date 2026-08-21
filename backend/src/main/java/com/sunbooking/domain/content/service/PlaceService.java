package com.sunbooking.domain.content.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunbooking.domain.content.dto.PlaceResponse;
import com.sunbooking.domain.content.repository.PlaceRepository;

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
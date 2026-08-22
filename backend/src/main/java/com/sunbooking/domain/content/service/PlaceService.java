package com.sunbooking.domain.content.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunbooking.domain.content.dto.PlaceRequest;
import com.sunbooking.domain.content.dto.PlaceResponse;
import com.sunbooking.domain.content.entity.Place;
import com.sunbooking.domain.content.repository.PlaceRepository;
import com.sunbooking.global.exception.ResourceNotFoundException;

@Service
public class PlaceService {

    private final PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @Transactional(readOnly = true)
    public List<PlaceResponse> findAll() {
        return placeRepository.findByStatusIgnoreCase("ACTIVE").stream()
                .map(PlaceResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public PlaceResponse findById(Long id) {
        return PlaceResponse.from(findActivePlace(id));
    }

    @Transactional(readOnly = true)
    public List<PlaceResponse> findAllAdmin() {
        return placeRepository.findAll().stream()
                .map(PlaceResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public PlaceResponse findByIdAdmin(Long id) {
        return PlaceResponse.from(findPlace(id));
    }

    @Transactional
    public PlaceResponse create(PlaceRequest request) {
        Place place = new Place();
        apply(place, request);
        return PlaceResponse.from(placeRepository.save(place));
    }

    @Transactional
    public PlaceResponse update(Long id, PlaceRequest request) {
        Place place = findPlace(id);
        apply(place, request);
        return PlaceResponse.from(placeRepository.save(place));
    }

    @Transactional
    public void delete(Long id) {
        Place place = findPlace(id);
        place.setStatus("INACTIVE");
        placeRepository.save(place);
    }

    private Place findPlace(Long id) {
        return placeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Place not found: " + id));
    }

    private Place findActivePlace(Long id) {
        Place place = findPlace(id);
        if (!"ACTIVE".equalsIgnoreCase(place.getStatus())) {
            throw new ResourceNotFoundException("Place not found: " + id);
        }
        return place;
    }

    private void apply(Place place, PlaceRequest request) {
        place.setName(request.name().trim());
        place.setDescription(request.description());
        place.setAddress(request.address());
        place.setCity(request.city());
        place.setImageUrl(request.imageUrl());
        place.setStatus(request.status());
    }
}
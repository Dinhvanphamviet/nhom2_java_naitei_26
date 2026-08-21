package com.sunbooking.domain.tour.service;

import com.sunbooking.domain.tour.dto.TourRequest;
import com.sunbooking.domain.tour.dto.TourResponse;
import com.sunbooking.domain.tour.entity.Category;
import com.sunbooking.domain.tour.entity.TourDeparture;
import com.sunbooking.domain.tour.entity.TourImage;
import com.sunbooking.domain.tour.entity.Tour;
import com.sunbooking.domain.tour.entity.TourStatus;
import com.sunbooking.global.exception.ResourceNotFoundException;
import com.sunbooking.domain.tour.repository.CategoryRepository;
import com.sunbooking.domain.tour.repository.TourRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TourService {

    private final TourRepository tourRepository;
    private final CategoryRepository categoryRepository;

    public TourService(TourRepository tourRepository, CategoryRepository categoryRepository) {
        this.tourRepository = tourRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<TourResponse> search(String keyword, Long categoryId) {
        String normalizedKeyword = keyword == null || keyword.isBlank() ? null : keyword.trim();
        return tourRepository.search(normalizedKeyword, categoryId).stream()
                .map(TourResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<TourResponse> search(String keyword, Long categoryId, Pageable pageable) {
        String normalizedKeyword = keyword == null || keyword.isBlank() ? null : keyword.trim();
        return tourRepository.search(normalizedKeyword, categoryId, pageable)
                .map(TourResponse::from);
    }

    @Transactional(readOnly = true)
    public TourResponse findById(Long id) {
        return TourResponse.from(findTour(id));
    }

    @Transactional
    public TourResponse create(TourRequest request) {
        Tour tour = new Tour();
        apply(tour, request);
        return TourResponse.from(tourRepository.save(tour));
    }

    @Transactional
    public TourResponse update(Long id, TourRequest request) {
        Tour tour = findTour(id);
        apply(tour, request);
        return TourResponse.from(tourRepository.save(tour));
    }

    @Transactional
    public void delete(Long id) {
        Tour tour = findTour(id);
        tour.setStatus(TourStatus.INACTIVE);
        tourRepository.save(tour);
    }

    private Tour findTour(Long id) {
        return tourRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tour not found: " + id));
    }

    private void apply(Tour tour, TourRequest request) {
        if (request.startDate().isAfter(request.endDate())) {
            throw new IllegalArgumentException("startDate must not be after endDate");
        }

        tour.setName(request.name().trim());
        tour.setDescription(request.description());
        tour.setBasePrice(request.basePrice());
        tour.setDeparture(request.departure());
        tour.setDestination(request.destination());
        tour.setDuration(request.duration());
        tour.setStatus(request.status());
        tour.setStartDate(request.startDate());
        tour.setEndDate(request.endDate());
        tour.setCategory(resolveCategory(request.categoryId()));
        updateImages(tour, request.images());
        updateDepartures(tour, request.departures());
    }

    private void updateImages(Tour tour, List<TourRequest.TourImageRequest> requests) {
        if (requests == null) {
            return;
        }
        tour.getImages().clear();
        requests.forEach(request -> {
            TourImage image = new TourImage();
            image.setImageUrl(request.imageUrl().trim());
            image.setTour(tour);
            tour.getImages().add(image);
        });
    }

    private void updateDepartures(Tour tour, List<TourRequest.TourDepartureRequest> requests) {
        if (requests == null) {
            return;
        }
        tour.getDepartures().clear();
        requests.forEach(request -> {
            if (request.departureDate().isAfter(request.returnDate())) {
                throw new IllegalArgumentException("departureDate must not be after returnDate");
            }
            if (request.availableSlot() > request.totalSlot()) {
                throw new IllegalArgumentException("availableSlot must not exceed totalSlot");
            }
            TourDeparture departure = new TourDeparture();
            departure.setDepartureDate(request.departureDate());
            departure.setReturnDate(request.returnDate());
            departure.setPrice(request.price());
            departure.setTotalSlot(request.totalSlot());
            departure.setAvailableSlot(request.availableSlot());
            departure.setStatus(request.status());
            departure.setTour(tour);
            tour.getDepartures().add(departure);
        });
    }

    private Category resolveCategory(Long categoryId) {
        if (categoryId == null) {
            return null;
        }
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + categoryId));
    }
}
package com.sunbooking.domain.tour.controller.admin;

import com.sunbooking.domain.tour.dto.TourRequest;
import com.sunbooking.domain.tour.dto.TourResponse;
import com.sunbooking.domain.tour.service.AdminTourService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/admin/tours")
@PreAuthorize("hasRole('ADMIN')")
public class AdminTourController {

    private static final Set<String> ALLOWED_SORT_PROPERTIES = Set.of(
            "id", "name", "basePrice", "departure", "destination", "duration",
            "status", "startDate", "endDate", "createdAt", "updatedAt");

    private final AdminTourService adminTourService;

    public AdminTourController(AdminTourService adminTourService) {
        this.adminTourService = adminTourService;
    }

    @GetMapping
    public Page<TourResponse> list(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort) {
        Pageable pageable = PageRequest.of(Math.max(page, 0), Math.max(size, 1), parseSort(sort));
        return adminTourService.findAll(keyword, categoryId, pageable);
    }

    private Sort parseSort(String sort) {
        if (sort == null || sort.isBlank()) {
            return Sort.unsorted();
        }

        String expression = normalizeSortExpression(sort);
        return expression.isBlank() ? Sort.unsorted() : Sort.by(toOrder(expression));
    }

    private String normalizeSortExpression(String expression) {
        return expression.trim()
                .replace("[", "")
                .replace("]", "")
                .replace("\"", "");
    }

    private Sort.Order toOrder(String expression) {
        String[] parts = expression.split(",", 2);
        String property = parts[0].trim();
        if (!ALLOWED_SORT_PROPERTIES.contains(property)) {
            throw new IllegalArgumentException("Invalid sort property: " + property);
        }
        Sort.Direction direction = parts.length > 1
                ? Sort.Direction.fromOptionalString(parts[1].trim()).orElse(Sort.Direction.ASC)
                : Sort.Direction.ASC;
        return new Sort.Order(direction, property);
    }

    @GetMapping("/{id}")
    public TourResponse get(@PathVariable Long id) {
        return adminTourService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TourResponse create(@Valid @RequestBody TourRequest request) {
        return adminTourService.create(request);
    }

    @PutMapping("/{id}")
    public TourResponse update(@PathVariable Long id, @Valid @RequestBody TourRequest request) {
        return adminTourService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        adminTourService.delete(id);
    }
}

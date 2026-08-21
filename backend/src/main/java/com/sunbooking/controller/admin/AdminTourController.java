package com.sunbooking.controller.admin;

import com.sunbooking.dto.tour.TourRequest;
import com.sunbooking.dto.tour.TourResponse;
import com.sunbooking.service.AdminTourService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

@RestController
@RequestMapping("/api/admin/tours")
@PreAuthorize("hasRole('ADMIN')")
public class AdminTourController {

    private final AdminTourService adminTourService;

    public AdminTourController(AdminTourService adminTourService) {
        this.adminTourService = adminTourService;
    }

    @GetMapping
    public Page<TourResponse> list(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId,
            Pageable pageable) {
        return adminTourService.findAll(keyword, categoryId, pageable);
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
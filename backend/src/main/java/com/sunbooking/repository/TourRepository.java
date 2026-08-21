package com.sunbooking.repository;

import com.sunbooking.entity.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TourRepository extends JpaRepository<Tour, Long> {

    @Query("select distinct t from Tour t left join fetch t.category left join fetch t.images "
        + "where (:keyword is null or lower(t.name) like lower(concat('%', :keyword, '%')) "
        + "or lower(t.destination) like lower(concat('%', :keyword, '%'))) "
        + "and (:categoryId is null or t.category.id = :categoryId)")
    List<Tour> search(@Param("keyword") String keyword, @Param("categoryId") Long categoryId);

    @Query("select t from Tour t "
        + "where (:keyword is null or lower(t.name) like lower(concat('%', :keyword, '%')) "
        + "or lower(t.destination) like lower(concat('%', :keyword, '%'))) "
        + "and (:categoryId is null or t.category.id = :categoryId)")
    Page<Tour> search(@Param("keyword") String keyword, @Param("categoryId") Long categoryId,
                      Pageable pageable);
}
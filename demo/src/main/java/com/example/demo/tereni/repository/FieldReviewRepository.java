package com.example.demo.tereni.repository;

import com.example.demo.tereni.model.FieldReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FieldReviewRepository extends JpaRepository<FieldReview, Long> {
    Optional<FieldReview> findByUserIdAndField_Id(Long userId, Long fieldId);
    List<FieldReview> findByFieldId(Long fieldId);
    List<FieldReview> findByUserId(Long userId);
    @Query("select avg(r.rating) from FieldReview r where r.field.id = :fieldId")
    Double findAverageRating(@Param("fieldId") Long fieldId);
}

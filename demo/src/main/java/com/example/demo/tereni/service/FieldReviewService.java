package com.example.demo.tereni.service;

import com.example.demo.tereni.model.Field;
import com.example.demo.tereni.repository.FieldRepository;
import com.example.demo.tereni.model.FieldReview;
import com.example.demo.tereni.model.User;
import com.example.demo.tereni.repository.FieldReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FieldReviewService {

    @Autowired
    private FieldReviewRepository reviewRepository;
    
    @Autowired
    private FieldRepository fieldRepository;

    public FieldReview addReview(User user, Field field, Integer rating, String comment) {
        Optional<FieldReview> existing = reviewRepository.findByUserIdAndField_Id(user.getId(), field.getId());
        FieldReview saved;

        //provera da li postoji recenzija za odabrani teren
        if (existing.isPresent()) {
            FieldReview r = existing.get();
            r.setRating(rating);
            r.setComment(comment);
            r.setCreatedAt(LocalDateTime.now());
            saved = reviewRepository.save(r);
        } else {
            FieldReview r = FieldReview.builder()
                    .user(user)
                    .field(field)
                    .rating(rating)
                    .comment(comment)
                    .createdAt(LocalDateTime.now())
                    .build();
            saved = reviewRepository.save(r);
        }

        //nakon što je recenzija sačuvana, izračunaj prosek
        Double avg = reviewRepository.findAverageRating(field.getId());
        if (avg != null) {
            field.setRating(avg);
            fieldRepository.save(field); // ažurira rating u tabeli fields
        }

        return saved;
    }
    
    

    public List<FieldReview> getReviewsForField(Long fieldId) {
        return reviewRepository.findByFieldId(fieldId);
    }
}

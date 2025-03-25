package com.example.movieapp.service;

import com.example.movieapp.entity.Review;
import com.example.movieapp.model.request.CreateReviewRequest;
import com.example.movieapp.model.request.UpdateReviewRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    public Page<Review> getReviewsByMovie(Integer movieId, Integer page, Integer pageSize) {
        return null;
    }

    public Review createReview(CreateReviewRequest request) {
        return null;
    }

    public Review updateReview(Integer id, UpdateReviewRequest request) {
        return null;
    }

    public void deleteReview(Integer id) {
    }
}

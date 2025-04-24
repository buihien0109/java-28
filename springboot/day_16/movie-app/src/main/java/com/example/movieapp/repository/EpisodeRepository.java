package com.example.movieapp.repository;

import com.example.movieapp.entity.Episode;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EpisodeRepository extends JpaRepository<Episode, Integer> {
    List<Episode> findByMovie_IdAndStatusOrderByDisplayOrderAsc(Integer id, Boolean status);

    Episode findByMovie_IdAndDisplayOrderAndStatus(Integer id, Integer displayOrder, Boolean status);

    boolean existsByMovie_IdAndDisplayOrder(Integer id, @NotNull(message = "Thứ tự tập phim không được để trống") Integer displayOrder);

    Page<Episode> findByMovie_Id(Integer movieId, Pageable pageable);
}
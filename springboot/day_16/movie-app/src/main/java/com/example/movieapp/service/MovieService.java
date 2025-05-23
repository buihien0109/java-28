package com.example.movieapp.service;

import com.example.movieapp.entity.Country;
import com.example.movieapp.entity.Movie;
import com.example.movieapp.exception.BadRequestException;
import com.example.movieapp.exception.NotFoundException;
import com.example.movieapp.model.enums.MovieType;
import com.example.movieapp.model.request.UpsertMovieRequest;
import com.example.movieapp.repository.*;
import com.github.slugify.Slugify;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final CountryRepository countryRepository;
    private final GenreRepository genreRepository;
    private final ActorRepository actorRepository;
    private final DirectorRepository directorRepository;
    private final CloudinaryService cloudinaryService;

    public List<Movie> findHotMovie(Boolean status, Integer limit) {
        return movieRepository.findHotMovie(status, limit);
    }

    public Page<Movie> findByType(MovieType type, Boolean status, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("publishedAt").descending());
        Page<Movie> moviePage = movieRepository.findByTypeAndStatus(type, status, pageable);
        return moviePage;
    }

    public Movie findMovieDetails(Integer id, String slug) {
        return movieRepository.findByIdAndSlugAndStatus(id, slug, true);
    }

    public Page<Movie> getAllMovies(Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("createdAt").descending());
        return movieRepository.findAll(pageable);
    }

    public Movie getMovieById(Integer id) {
        return movieRepository.findById(id).orElse(null);
    }

    public Movie createMovie(UpsertMovieRequest request) {
        Slugify slugify = Slugify.builder().build();
        Country country = countryRepository.findById(request.getCountryId())
                .orElseThrow(() -> new NotFoundException("Quốc gia không tồn tại"));

        Movie movie = Movie.builder()
                .name(request.getName())
                .slug(slugify.slugify(request.getName()))
                .description(request.getDescription())
                .thumbnail("https://placehold.co/600x400?text=" + request.getName().substring(0, 1).toUpperCase())
                .releaseYear(request.getReleaseYear())
                .trailer(request.getTrailerUrl())
                .type(request.getType())
                .status(request.getStatus())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .publishedAt(request.getStatus() ? LocalDateTime.now() : null)
                .country(country)
                .genres(genreRepository.findAllById(request.getGenreIds()))
                .actors(actorRepository.findAllById(request.getActorIds()))
                .directors(directorRepository.findAllById(request.getDirectorIds()))
                .build();
        return movieRepository.save(movie);
    }

    public Movie updateMovie(Integer id, UpsertMovieRequest request) {
        Slugify slugify = Slugify.builder().build();
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy phim với id " + id));

        Country country = countryRepository.findById(request.getCountryId())
                .orElseThrow(() -> new NotFoundException("Quốc gia không tồn tại"));

        movie.setName(request.getName());
        movie.setSlug(slugify.slugify(request.getName()));
        movie.setDescription(request.getDescription());
        movie.setReleaseYear(request.getReleaseYear());
        movie.setTrailer(request.getTrailerUrl());
        movie.setType(request.getType());
        movie.setStatus(request.getStatus());
        movie.setUpdatedAt(LocalDateTime.now());
        movie.setPublishedAt(request.getStatus() ? LocalDateTime.now() : null);
        movie.setCountry(country);
        movie.setGenres(genreRepository.findAllById(request.getGenreIds()));
        movie.setActors(actorRepository.findAllById(request.getActorIds()));
        movie.setDirectors(directorRepository.findAllById(request.getDirectorIds()));
        return movieRepository.save(movie);
    }

    public void deleteMovie(Integer id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy phim với id " + id));
        movieRepository.delete(movie);
    }

    public Map uploadThumbnail(Integer id, MultipartFile file) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy phim với id " + id));
        try {
            Map map = cloudinaryService.uploadFile(file, "file_java_27_28");

            String url = map.get("url").toString();
            movie.setThumbnail(url);
            movie.setUpdatedAt(LocalDateTime.now());
            movieRepository.save(movie);

            return Map.of("url", url);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}

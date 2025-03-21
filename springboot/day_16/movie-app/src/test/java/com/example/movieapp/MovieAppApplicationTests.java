package com.example.movieapp;

import com.example.movieapp.entity.*;
import com.example.movieapp.model.enums.MovieType;
import com.example.movieapp.repository.*;
import com.github.javafaker.Faker;
import com.github.slugify.Slugify;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@SpringBootTest
class MovieAppApplicationTests {

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private EpisodeRepository episodeRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void save_countries() {
        Faker faker = new Faker();
        Slugify slugify = Slugify.builder().build();
        for (int i = 0; i < 10; i++) {
            String name = faker.country().name();
            Country country = Country.builder()
                    .name(name)
                    .slug(slugify.slugify(name))
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            countryRepository.save(country);
        }
    }

    @Test
    void save_genres() {
        Faker faker = new Faker();
        Slugify slugify = Slugify.builder().build();
        for (int i = 0; i < 10; i++) {
            String name = faker.leagueOfLegends().champion();
            Genre genre = Genre.builder()
                    .name(name)
                    .slug(slugify.slugify(name))
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            genreRepository.save(genre);
        }
    }

    @Test
    void save_actors() {
        Faker faker = new Faker();
        Slugify slugify = Slugify.builder().build();
        for (int i = 0; i < 100; i++) {
            String name = faker.name().fullName();
            Actor actor = Actor.builder()
                    .name(name)
                    .slug(slugify.slugify(name))
                    .avatar("https://placehold.co/600x400?text=" + name.substring(0, 1).toUpperCase())
                    .bio(faker.lorem().paragraph())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            actorRepository.save(actor);
        }
    }

    @Test
    void save_directors() {
        Faker faker = new Faker();
        Slugify slugify = Slugify.builder().build();
        for (int i = 0; i < 30; i++) {
            String name = faker.name().fullName();
            Director director = Director.builder()
                    .name(name)
                    .slug(slugify.slugify(name))
                    .avatar("https://placehold.co/600x400?text=" + name.substring(0, 1).toUpperCase())
                    .bio(faker.lorem().paragraph())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            directorRepository.save(director);
        }
    }

    @Test
    void save_users() {

    }

    @Test
    void save_movies() {
        Faker faker = new Faker();
        Slugify slugify = Slugify.builder().build();
        Random rd = new Random();

        for (int i = 0; i < 150; i++) {
            // Tao entity
            String name = faker.funnyName().name();
            String thumbnail = "https://placehold.co/600x400?text=" + name.substring(0, 1).toUpperCase();
            Boolean status = faker.bool().bool();

            int rdIndexType = rd.nextInt(MovieType.values().length);
            MovieType type = MovieType.values()[rdIndexType];

            Movie movie = Movie.builder()
                    .name(name)
                    .slug(slugify.slugify(name))
                    .description(faker.lorem().paragraph())
                    .thumbnail(thumbnail)
                    .releaseYear(faker.number().numberBetween(1990, 2021))
                    .trailer("https://www.youtube.com/embed/W_0AMP9yO1w?si=JcCeGorHalCHKCPl")
                    .status(status)
                    .rating(faker.number().randomDouble(1, 5, 10))
                    .type(type)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .publishedAt(status ? LocalDateTime.now() : null)
                    .build();

            // Luu vao DB
            movieRepository.save(movie);
        }
    }

    @Test
    void testQuery() {
        // Movie movie = movieRepository.findByName("Dinah Soares");
        // System.out.println(movie);

        // Sap xep
//        List<Movie> movieSortByRating = movieRepository
//                .findByRatingLessThan(8.0, Sort.by("rating").descending().and(Sort.by("name").ascending()));
//        movieSortByRating.forEach(movie -> System.out.println(movie.getName() + " - " + movie.getRating()));

        // Phan trang
        Pageable pageable = PageRequest.of(0, 5, Sort.by("rating").descending());
        Page<Movie> moviePage = movieRepository.findByNameContaining("a", pageable);
        System.out.println("Total pages: " + moviePage.getTotalPages());
        System.out.println("Total elements: " + moviePage.getTotalElements());
        moviePage.getContent().forEach(movie -> System.out.println(movie.getName() + " - " + movie.getRating()));
    }

}

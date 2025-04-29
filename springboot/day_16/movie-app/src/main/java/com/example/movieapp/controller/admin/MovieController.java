package com.example.movieapp.controller.admin;

import com.example.movieapp.entity.Movie;
import com.example.movieapp.repository.ActorRepository;
import com.example.movieapp.repository.CountryRepository;
import com.example.movieapp.repository.DirectorRepository;
import com.example.movieapp.repository.GenreRepository;
import com.example.movieapp.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/movies")
@RequiredArgsConstructor
public class MovieController {
    private final CountryRepository countryRepository;
    private final ActorRepository actorRepository;
    private final DirectorRepository directorRepository;
    private final GenreRepository genreRepository;
    private final MovieService movieService;

    @GetMapping
    public String getIndexPage() {
        return "admin/movie/index";
    }

    @GetMapping("/create")
    public String getCreatePage(Model model) {
        model.addAttribute("countries", countryRepository.findAll());
        model.addAttribute("actors", actorRepository.findAll());
        model.addAttribute("directors", directorRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());
        return "admin/movie/create";
    }

    @GetMapping("/{id}")
    public String getDetailsPage(@PathVariable Integer id,  Model model) {
        model.addAttribute("movie", movieService.getMovieById(id));
        model.addAttribute("directors", directorRepository.findAll());
        model.addAttribute("actors", actorRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());
        model.addAttribute("countries", countryRepository.findAll());
        return "admin/movie/detail";
    }
}

package com.example.movieapp.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/movies")
public class MovieController {
    @GetMapping
    public String getIndexPage() {
        return "admin/movie/index";
    }

    @GetMapping("/create")
    public String getCreatePage() {
        return "admin/movie/create";
    }

    @GetMapping("/{id}")
    public String getDetailsPage(@PathVariable Long id) {
        return "admin/movie/detail";
    }
}

package com.example.demothymeleaf.comtroller;

import com.example.demothymeleaf.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WebController {
    private List<Person> people = new ArrayList<>(List.of(
            new Person(1, "Nguyen Van A", "M", 20),
            new Person(2, "Tran Van B", "M", 4),
            new Person(3, "Ngo Thi C", "F", 9),
            new Person(4, "Nguyen Thi D", "F", 18),
            new Person(5, "Nguyen Van E", "M", 24)
    ));
    @GetMapping("/") // http://localhost:8080
    public String getHome(Model model) {
        model.addAttribute("people", people);
        model.addAttribute("person", people.get(0));

        model.addAttribute("name", "Nguyen Van A");
        model.addAttribute("salary", 1000);
        return "index";
    }
}

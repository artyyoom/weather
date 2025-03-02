package ru.art.weather.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FirstController {

    @GetMapping("/hello")
    public String helloPage(HttpServletRequest request, Model model) {

        String name = request.getParameter("name");
        String surname = request.getParameter("surname");

        model.addAttribute("message", "Hello " + name + " " + surname);

        return "first/hello";
    }

    @GetMapping("/goodbye")
    // Будет ошибка если parameter будет null, исправить required = false
    public String goodByePage(@RequestParam(value = "name", required = false) String name,
                              @RequestParam("surname") String surname,
                              Model model) {

        model.addAttribute("name", name);

        return "first/goodbye";
    }
}

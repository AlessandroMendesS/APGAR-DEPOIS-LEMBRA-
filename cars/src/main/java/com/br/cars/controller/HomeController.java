package com.br.cars.controller;
import com.br.cars.service.CarroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final CarroService carroService;

    public HomeController(CarroService carroService) {
        this.carroService = carroService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("carros", carroService.buscarDisponiveis());
        return "index";
    }
}
package com.br.cars.controller;

import com.br.cars.model.Carro;
import com.br.cars.service.CarroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/carros")
public class CarroViewController {
    private final CarroService carroService;

    public CarroViewController(CarroService carroService) {
        this.carroService = carroService;
    }

    @GetMapping("/{id}")
    public String detalhes(@PathVariable Long id, Model model) {
        Optional<Carro> carro = carroService.buscarPorId(id);
        if (carro.isEmpty()) {
            return "redirect:/";
        }

        model.addAttribute("carro", carro.get());
        return "carro/detalhes";
    }
}
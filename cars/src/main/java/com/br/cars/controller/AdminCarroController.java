package com.br.cars.controller;

import com.br.cars.model.Carro;
import com.br.cars.service.CarroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin/carros")
public class AdminCarroController {
    private final CarroService carroService;

    public AdminCarroController(CarroService carroService) {
        this.carroService = carroService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("carros", carroService.listarTodos());
        return "admin/carros/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("carro", new Carro());
        return "admin/carros/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Optional<Carro> carro = carroService.buscarPorId(id);
        if (carro.isEmpty()) {
            return "redirect:/admin/carros";
        }

        model.addAttribute("carro", carro.get());
        return "admin/carros/form";
    }

    @PostMapping
    public String salvar(@ModelAttribute Carro carro) {
        carroService.salvar(carro);
        return "redirect:/admin/carros";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute Carro carro) {
        carro.setId(id);
        carroService.salvar(carro);
        return "redirect:/admin/carros";
    }

    @PostMapping("/{id}/delete")
    public String deletar(@PathVariable Long id) {
        carroService.deletar(id);
        return "redirect:/admin/carros";
    }
}
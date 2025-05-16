package com.br.cars.controller;
import com.br.cars.model.Carro;
import com.br.cars.service.CarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/carros")
public class CarroController {
    @Autowired
    private CarroService carroService;

    @GetMapping
    public List<Carro> listarTodos() {
        return carroService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carro> buscarPorId(@PathVariable Long id) {
        return carroService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Carro salvar(@RequestBody Carro carro) {
        return carroService.salvar(carro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carro> atualizar(@PathVariable Long id, @RequestBody Carro carro) {
        if (!carroService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        carro.setId(id);
        return ResponseEntity.ok(carroService.salvar(carro));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!carroService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        carroService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/imagens")
    public ResponseEntity<Void> adicionarImagem(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        Carro carro = carroService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Carro não encontrado"));

        carroService.adicionarImagem(carro, file);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/video")
    public ResponseEntity<Void> adicionarVideo(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        Carro carro = carroService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Carro não encontrado"));

        carroService.adicionarVideo(carro, file);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/disponiveis")
    public List<Carro> buscarDisponiveis() {
        return carroService.buscarDisponiveis();
    }

    @GetMapping("/marca/{marca}")
    public List<Carro> buscarPorMarca(@PathVariable String marca) {
        return carroService.buscarPorMarca(marca);
    }

    @GetMapping("/modelo/{modelo}")
    public List<Carro> buscarPorModelo(@PathVariable String modelo) {
        return carroService.buscarPorModelo(modelo);
    }

    @GetMapping("/preco")
    public List<Carro> buscarPorFaixaDePreco(@RequestParam Double min, @RequestParam Double max) {
        return carroService.buscarPorFaixaDePreco(min, max);
    }
}
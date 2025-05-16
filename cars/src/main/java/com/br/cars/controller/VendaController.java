package com.br.cars.controller;

import com.br.cars.model.Carro;
import com.br.cars.model.Usuario;
import com.br.cars.model.Venda;
import com.br.cars.service.CarroService;
import com.br.cars.service.UsuarioService;
import com.br.cars.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/vendas")
public class VendaController {
    @Autowired
    private VendaService vendaService;

    @Autowired
    private CarroService carroService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Venda> registrarVenda(
            @RequestParam Long carroId,
            @RequestParam Double valorVenda,
            @AuthenticationPrincipal UserDetails userDetails) {

        Carro carro = carroService.buscarPorId(carroId)
                .orElseThrow(() -> new RuntimeException("Carro não encontrado"));

        Usuario vendedor = usuarioService.buscarPorEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Venda venda = vendaService.registrarVenda(carro, vendedor, valorVenda);
        return ResponseEntity.ok(venda);
    }

    @GetMapping("/periodo")
    public List<Venda> listarPorPeriodo(
            @RequestParam String inicio,
            @RequestParam String fim) {

        LocalDateTime dataInicio = LocalDateTime.parse(inicio);
        LocalDateTime dataFim = LocalDateTime.parse(fim);

        return vendaService.listarVendasPorPeriodo(dataInicio, dataFim);
    }

    @GetMapping("/vendedor/{vendedorId}")
    public List<Venda> listarPorVendedor(@PathVariable Long vendedorId) {
        return vendaService.listarVendasPorVendedor(vendedorId);
    }
}
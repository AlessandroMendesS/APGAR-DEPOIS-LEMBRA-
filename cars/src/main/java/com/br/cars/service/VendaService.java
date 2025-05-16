package com.br.cars.service;
import com.br.cars.model.Carro;
import com.br.cars.model.Usuario;
import com.br.cars.model.Venda;
import com.br.cars.repository.CarroRepository;
import com.br.cars.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VendaService {
    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private CarroRepository carroRepository;

    public Venda registrarVenda(Carro carro, Usuario vendedor, Double valorVenda) {
        Venda venda = new Venda();
        venda.setCarro(carro);
        venda.setVendedor(vendedor);
        venda.setDataVenda(LocalDateTime.now());
        venda.setValorVenda(valorVenda);

        carro.setVendido(true);
        carroRepository.save(carro);

        return vendaRepository.save(venda);
    }

    public List<Venda> listarVendasPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return vendaRepository.findByPeriodo(inicio, fim);
    }

    public List<Venda> listarVendasPorVendedor(Long vendedorId) {
        return vendaRepository.findByVendedorId(vendedorId);
    }
}
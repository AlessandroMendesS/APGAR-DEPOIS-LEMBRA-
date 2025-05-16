package com.br.cars.repository;
import com.br.cars.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {
    @Query("SELECT v FROM Venda v WHERE v.dataVenda BETWEEN :inicio AND :fim")
    List<Venda> findByPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    List<Venda> findByVendedorId(Long vendedorId);
}
package com.br.cars.repository;
import com.br.cars.model.Carro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarroRepository extends JpaRepository<Carro, Long> {
    List<Carro> findByMarcaContainingIgnoreCase(String marca);
    List<Carro> findByModeloContainingIgnoreCase(String modelo);

    @Query("SELECT c FROM Carro c WHERE c.valor BETWEEN :min AND :max")
    List<Carro> findByFaixaDePreco(@Param("min") Double min, @Param("max") Double max);

    List<Carro> findByVendido(Boolean vendido);
}
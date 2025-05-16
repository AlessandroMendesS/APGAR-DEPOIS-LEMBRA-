package com.br.cars.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ImagemCarro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeArquivo;
    private String tipoArquivo;

    @Lob
    private byte[] dados;

    @ManyToOne
    @JoinColumn(name = "carro_id")
    private Carro carro;
}
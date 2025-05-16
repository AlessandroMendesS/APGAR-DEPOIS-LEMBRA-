package com.br.cars.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class VideoCarro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeArquivo;
    private String tipoArquivo;

    @Lob
    private byte[] dados;

    @OneToOne
    @JoinColumn(name = "carro_id")
    private Carro carro;
}
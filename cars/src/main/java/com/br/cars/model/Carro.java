package com.br.cars.model;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Carro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marca;
    private String modelo;
    private Integer ano;
    private String cor;
    private Double quilometragem;
    private Double valor;
    private String descricao;
    private Boolean vendido = false;

    @OneToMany(mappedBy = "carro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImagemCarro> imagens;

    @OneToOne(mappedBy = "carro", cascade = CascadeType.ALL, orphanRemoval = true)
    private VideoCarro video;

    @OneToOne(mappedBy = "carro")
    private Venda venda;


}
package com.br.cars.service;
import com.br.cars.model.Carro;
import com.br.cars.model.ImagemCarro;
import com.br.cars.model.VideoCarro;
import com.br.cars.repository.CarroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CarroService {
    @Autowired
    private CarroRepository carroRepository;

    public List<Carro> listarTodos() {
        return carroRepository.findAll();
    }

    public Optional<Carro> buscarPorId(Long id) {
        return carroRepository.findById(id);
    }

    public Carro salvar(Carro carro) {
        return carroRepository.save(carro);
    }

    public void deletar(Long id) {
        carroRepository.deleteById(id);
    }

    public List<Carro> buscarPorMarca(String marca) {
        return carroRepository.findByMarcaContainingIgnoreCase(marca);
    }

    public List<Carro> buscarPorModelo(String modelo) {
        return carroRepository.findByModeloContainingIgnoreCase(modelo);
    }

    public List<Carro> buscarPorFaixaDePreco(Double min, Double max) {
        return carroRepository.findByFaixaDePreco(min, max);
    }

    public List<Carro> buscarDisponiveis() {
        return carroRepository.findByVendido(false);
    }

    public void adicionarImagem(Carro carro, MultipartFile file) throws IOException {
        ImagemCarro imagem = new ImagemCarro();
        imagem.setNomeArquivo(file.getOriginalFilename());
        imagem.setTipoArquivo(file.getContentType());
        imagem.setDados(file.getBytes());
        imagem.setCarro(carro);

        carro.getImagens().add(imagem);
        carroRepository.save(carro);
    }

    public void adicionarVideo(Carro carro, MultipartFile file) throws IOException {
        VideoCarro video = new VideoCarro();
        video.setNomeArquivo(file.getOriginalFilename());
        video.setTipoArquivo(file.getContentType());
        video.setDados(file.getBytes());
        video.setCarro(carro);

        carro.setVideo(video);
        carroRepository.save(carro);
    }
}
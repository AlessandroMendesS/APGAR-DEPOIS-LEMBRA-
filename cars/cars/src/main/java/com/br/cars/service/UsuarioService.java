package com.br.cars.service;

import com.br.cars.model.Usuario;
import com.br.cars.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Método para salvar o usuário
    public Usuario salvar(Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha())); // Criptografa a senha
        return usuarioRepository.save(usuario);
    }

    // Método para buscar o usuário por email
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // Implementação do método loadUserByUsername
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Busca o usuário no banco de dados pelo email
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));

        // Retorna um UserDetails (aqui usando a classe User do Spring Security)
        return User.builder()
                .username(usuario.getEmail())    // O nome de usuário será o email
                .password(usuario.getSenha())    // A senha será a criptografada
                .roles("USER")                  // Aqui você pode configurar os roles, por exemplo, USER
                .build();
    }
}

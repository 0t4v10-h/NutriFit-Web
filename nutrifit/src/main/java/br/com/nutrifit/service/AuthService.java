package br.com.nutrifit.service;

import br.com.nutrifit.model.Usuario;
import br.com.nutrifit.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;

    public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario autenticar(String email, String senha) {

        Optional<Usuario> usuarioOpt =
                usuarioRepository.findByEmail(email);

        if (usuarioOpt.isPresent()) {

            Usuario usuario = usuarioOpt.get();

            if (usuario.getSenha().equals(senha)) {
                return usuario;
            }
        }

        return null;
    }
}
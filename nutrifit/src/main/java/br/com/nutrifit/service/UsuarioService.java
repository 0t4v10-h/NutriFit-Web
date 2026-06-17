package br.com.nutrifit.service;

import br.com.nutrifit.model.Usuario;
import br.com.nutrifit.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<Usuario> listarTodos() {
        return repository.findAll();
    }

    public Usuario salvar(Usuario usuario) {
        return repository.save(usuario);
    }

    public Usuario buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public boolean emailJaCadastrado(String email, Long idAtual) {

        return repository.findByEmail(email)
                .map(Usuario::getId)
                .filter(id -> !id.equals(idAtual))
                .isPresent();
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
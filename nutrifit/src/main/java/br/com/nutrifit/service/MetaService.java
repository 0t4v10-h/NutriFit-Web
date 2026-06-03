package br.com.nutrifit.service;

import br.com.nutrifit.model.Meta;
import br.com.nutrifit.repository.MetaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetaService {

    private final MetaRepository repository;

    public MetaService(MetaRepository repository) {
        this.repository = repository;
    }

    public List<Meta> listarTodos() {
        return repository.findAll();
    }

    public Meta salvar(Meta meta) {
        return repository.save(meta);
    }

    public Meta buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
package br.com.nutrifit.service;

import br.com.nutrifit.model.Treino;
import br.com.nutrifit.repository.TreinoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreinoService {

    private final TreinoRepository repository;

    public TreinoService(TreinoRepository repository) {
        this.repository = repository;
    }

    public List<Treino> listarTodos() {
        return repository.findAll();
    }

    public Treino buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Treino salvar(Treino treino) {
        return repository.save(treino);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
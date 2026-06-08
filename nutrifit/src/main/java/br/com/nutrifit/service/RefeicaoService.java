package br.com.nutrifit.service;

import br.com.nutrifit.model.Refeicao;
import br.com.nutrifit.repository.RefeicaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RefeicaoService {

    private final RefeicaoRepository repository;

    public RefeicaoService(RefeicaoRepository repository) {
        this.repository = repository;
    }

    public List<Refeicao> listarTodos() {
        return repository.findAll();
    }

    public Refeicao salvar(Refeicao refeicao) {
        return repository.save(refeicao);
    }

    public Refeicao buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
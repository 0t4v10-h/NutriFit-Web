package br.com.nutrifit.service;

import br.com.nutrifit.model.Agendamento;
import br.com.nutrifit.repository.AgendamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendamentoService {

    private final AgendamentoRepository repository;

    public AgendamentoService(
            AgendamentoRepository repository) {

        this.repository = repository;
    }

    public List<Agendamento> listarTodos() {
        return repository.findAll();
    }

    public Agendamento buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Agendamento salvar(Agendamento agendamento) {
        return repository.save(agendamento);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
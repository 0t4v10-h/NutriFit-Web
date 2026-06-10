package br.com.nutrifit.repository;

import br.com.nutrifit.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findByUsuarioId(Long usuarioId);
}
package br.com.nutrifit.repository;

import br.com.nutrifit.model.Refeicao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RefeicaoRepository extends JpaRepository<Refeicao, Long> {
    List<Refeicao> findByUsuarioId(Long usuarioId);
}
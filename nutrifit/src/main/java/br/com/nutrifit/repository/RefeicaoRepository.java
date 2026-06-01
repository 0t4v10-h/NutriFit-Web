package br.com.nutrifit.repository;

import br.com.nutrifit.model.Refeicao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefeicaoRepository extends JpaRepository<Refeicao, Long> {
}
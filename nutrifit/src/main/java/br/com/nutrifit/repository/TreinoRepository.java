package br.com.nutrifit.repository;

import br.com.nutrifit.model.Treino;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TreinoRepository extends JpaRepository<Treino, Long> {
    List<Treino> findByUsuarioId(Long usuarioId);
}
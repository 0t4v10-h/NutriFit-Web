package br.com.nutrifit.repository;

import br.com.nutrifit.model.Meta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MetaRepository extends JpaRepository<Meta, Long> {

    Optional<Meta> findByUsuarioId(Long usuarioId);

}
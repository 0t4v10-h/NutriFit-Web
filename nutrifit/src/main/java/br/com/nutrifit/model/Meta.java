package br.com.nutrifit.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "metas")
public class Meta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A descrição da meta é obrigatória")
    private String descricao;

    @NotNull(message = "O peso objetivo é obrigatório")
    private Double pesoObjetivo;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
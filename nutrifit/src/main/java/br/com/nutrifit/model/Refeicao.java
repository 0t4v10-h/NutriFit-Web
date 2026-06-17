package br.com.nutrifit.model;

import br.com.nutrifit.model.enums.TipoRefeicao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "refeicoes")
public class Refeicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da refeição é obrigatório")
    private String nome;

    @NotNull(message = "As calorias são obrigatórias")
    @Positive(message = "As calorias devem ser maiores que zero")
    private Double calorias;

    @NotNull(message = "Selecione o tipo da refeição")
    @Enumerated(EnumType.STRING)
    private TipoRefeicao tipo;

    @NotNull(message = "A data da refeição é obrigatória")
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
package br.com.nutrifit.model;

import br.com.nutrifit.model.enums.ObjetivoNutricional;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.ArrayList;
import br.com.nutrifit.model.enums.PerfilUsuario;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuario extends Pessoa {

    @Enumerated(EnumType.STRING)
    private PerfilUsuario perfil;

    @NotNull(message = "O peso é obrigatório")
    @DecimalMin(value = "1.0", message = "Peso inválido")
    private Double peso;

    @NotNull(message = "A altura é obrigatória")
    @DecimalMin(value = "0.50", message = "Altura inválida")
    @DecimalMax(value = "2.50", message = "Altura inválida")
    private Double altura;

    @NotNull(message = "A meta calórica é obrigatória")
    @DecimalMin(value = "1.0", message = "Meta calórica inválida")
    private Double metaCalorica;

    @Enumerated(EnumType.STRING)
    private ObjetivoNutricional objetivo;

    @OneToOne(mappedBy = "usuario")
    private Meta meta;

    @OneToMany(mappedBy = "usuario")
    private List<Refeicao> refeicoes;

    @OneToMany(mappedBy = "usuario")
    private List<Treino> treinos;

    @OneToMany(mappedBy = "usuario")
    private List<Agendamento> agendamentos;

}
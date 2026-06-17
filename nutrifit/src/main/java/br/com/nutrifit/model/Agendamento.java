package br.com.nutrifit.model;

import br.com.nutrifit.model.enums.StatusAgendamento;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "agendamentos")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A data do agendamento é obrigatória")
    @FutureOrPresent(message = "A data não pode estar no passado")
    private LocalDate data;

    @NotNull(message = "O horário do agendamento é obrigatório")
    private LocalTime horario;

    @NotNull(message = "Selecione o status do agendamento")
    @Enumerated(EnumType.STRING)
    private StatusAgendamento status;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
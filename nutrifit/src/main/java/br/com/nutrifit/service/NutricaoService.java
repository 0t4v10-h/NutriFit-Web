package br.com.nutrifit.service;

import br.com.nutrifit.model.Refeicao;
import br.com.nutrifit.model.Usuario;
import br.com.nutrifit.repository.RefeicaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NutricaoService {

    private final RefeicaoRepository refeicaoRepository;

    public NutricaoService(RefeicaoRepository refeicaoRepository) {
        this.refeicaoRepository = refeicaoRepository;
    }

    public double calcularCaloriasConsumidas(Usuario usuario) {

        List<Refeicao> refeicoes =
                refeicaoRepository.findByUsuarioId(
                        usuario.getId());

        return refeicoes.stream()
                .mapToDouble(Refeicao::getCalorias)
                .sum();
    }

    public double calcularPercentualMeta(Usuario usuario) {

        double consumidas =
                calcularCaloriasConsumidas(usuario);

        if (usuario.getMetaCalorica() == null
                || usuario.getMetaCalorica() == 0) {
            return 0;
        }

        double percentual =
                (consumidas / usuario.getMetaCalorica()) * 100;

        return Math.min(percentual, 100);
    }

    public String obterCorProgresso(Usuario usuario) {

        double percentual =
                calcularPercentualMeta(usuario);

        if (percentual <= 80) {
            return "bg-success";
        }

        if (percentual <= 100) {
            return "bg-warning";
        }

        return "bg-danger";
    }

    public String gerarRecomendacao(Usuario usuario) {

        double consumidas =
                calcularCaloriasConsumidas(usuario);

        double meta =
                usuario.getMetaCalorica();

        if (consumidas == 0) {
            return "Nenhuma refeição registrada.";
        }

        if (consumidas > meta) {
            return "Consumo calórico acima da meta diária.";
        }

        if (consumidas >= meta * 0.7) {
            return "Você está próximo de atingir sua meta.";
        }

        return "Você ainda está abaixo da meta diária.";
    }
}
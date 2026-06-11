package br.com.nutrifit.controller;

import br.com.nutrifit.model.Usuario;
import br.com.nutrifit.model.enums.PerfilUsuario;
import br.com.nutrifit.service.IMCService;
import br.com.nutrifit.service.NutricaoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final IMCService imcService;
    private final NutricaoService nutricaoService;

    public DashboardController(
            IMCService imcService,
            NutricaoService nutricaoService) {

        this.imcService = imcService;
        this.nutricaoService = nutricaoService;
    }

    @GetMapping("/dashboard")
    public String dashboard(
            HttpSession session,
            Model model) {

        Usuario usuario =
                (Usuario) session.getAttribute("usuarioLogado");

        if (usuario == null) {
            return "redirect:/login";
        }

        if (usuario.getPerfil() != PerfilUsuario.USUARIO) {
            return "redirect:/admin/dashboard";
        }

        double imc = imcService.calcularIMC(
                usuario.getPeso(),
                usuario.getAltura());

        String classificacao =
                imcService.classificarIMC(imc);

        String mensagemImc;

        String classificacaoLower =
                classificacao.toLowerCase();

        if (classificacaoLower.contains("abaixo")) {

            mensagemImc =
                    "Seu IMC está abaixo do ideal.";

        } else if (classificacaoLower.contains("normal")) {

            mensagemImc =
                    "Seu IMC está dentro da faixa saudável.";

        } else {

            mensagemImc =
                    "Atenção: seu IMC está acima do recomendado.";
        }

        double caloriasConsumidas =
                nutricaoService.calcularCaloriasConsumidas(usuario);

        double percentual =
                nutricaoService.calcularPercentualMeta(usuario);

        String recomendacao =
                nutricaoService.gerarRecomendacao(usuario);

        String corProgresso =
                nutricaoService.obterCorProgresso(usuario);

        model.addAttribute("usuario", usuario);
        model.addAttribute("imc", String.format("%.2f", imc));
        model.addAttribute("classificacao", classificacao);

        model.addAttribute(
                "mensagemImc",
                mensagemImc);

        model.addAttribute(
                "caloriasConsumidas",
                Math.round(caloriasConsumidas));

        model.addAttribute(
                "percentualMeta",
                Math.round(percentual));

        model.addAttribute(
                "recomendacao",
                recomendacao);

        model.addAttribute(
                "corProgresso",
                corProgresso);

        return "dashboard-usuario";
    }
}
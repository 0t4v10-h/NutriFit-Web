package br.com.nutrifit.controller;

import br.com.nutrifit.model.Usuario;
import br.com.nutrifit.model.enums.PerfilUsuario;
import br.com.nutrifit.service.IMCService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final IMCService imcService;

    public DashboardController(IMCService imcService) {
        this.imcService = imcService;
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

        model.addAttribute("usuario", usuario);
        model.addAttribute("imc", String.format("%.2f", imc));
        model.addAttribute("classificacao", classificacao);

        return "dashboard-usuario";
    }
}
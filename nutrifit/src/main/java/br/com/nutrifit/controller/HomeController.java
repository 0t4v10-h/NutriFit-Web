package br.com.nutrifit.controller;

import br.com.nutrifit.model.Usuario;
import br.com.nutrifit.model.enums.PerfilUsuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(HttpSession session) {

        Usuario usuario =
                (Usuario) session.getAttribute("usuarioLogado");

        if (usuario == null) {
            return "redirect:/login";
        }

        if (usuario.getPerfil() == PerfilUsuario.ADMIN) {
            return "redirect:/admin/dashboard";
        }

        return "redirect:/dashboard";
    }
}
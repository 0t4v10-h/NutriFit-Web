package br.com.nutrifit.controller;

import br.com.nutrifit.model.Usuario;
import br.com.nutrifit.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import br.com.nutrifit.model.enums.PerfilUsuario;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String email,
            @RequestParam String senha,
            HttpSession session,
            Model model) {

        Usuario usuario =
                authService.autenticar(email, senha);

        if (usuario == null) {

            model.addAttribute(
                    "erro",
                    "Email ou senha inválidos");

            return "login";
        }

        session.setAttribute("usuarioLogado", usuario);

        if (usuario.getPerfil() == PerfilUsuario.ADMIN) {
            return "redirect:/admin/dashboard";
        }

        return "redirect:/dashboard";
    }
}
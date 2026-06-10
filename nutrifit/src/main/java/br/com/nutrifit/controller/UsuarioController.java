package br.com.nutrifit.controller;

import br.com.nutrifit.model.Usuario;
import br.com.nutrifit.model.enums.PerfilUsuario;
import br.com.nutrifit.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    private String validarAdmin(HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");

        if (usuario == null || usuario.getPerfil() != PerfilUsuario.ADMIN) {
            return "redirect:/dashboard";
        }

        return null;
    }

    @GetMapping
    public String listar(Model model, HttpSession session) {

        String validacao = validarAdmin(session);

        if (validacao != null) {
            return validacao;
        }

        model.addAttribute("usuarios", service.listarTodos());

        return "usuarios/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model, HttpSession session) {

        String validacao = validarAdmin(session);

        if (validacao != null) {
            return validacao;
        }

        model.addAttribute("usuario", new Usuario());

        return "usuarios/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Usuario usuario,
                         BindingResult result,
                         HttpSession session) {

        String validacao = validarAdmin(session);

        if (validacao != null) {
            return validacao;
        }

        if (result.hasErrors()) {
            return "usuarios/form";
        }

        service.salvar(usuario);

        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id,
                         Model model,
                         HttpSession session) {

        String validacao = validarAdmin(session);

        if (validacao != null) {
            return validacao;
        }

        Usuario usuario = service.buscarPorId(id);

        model.addAttribute("usuario", usuario);

        return "usuarios/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id,
                          HttpSession session) {

        String validacao = validarAdmin(session);

        if (validacao != null) {
            return validacao;
        }

        service.excluir(id);

        return "redirect:/usuarios";
    }
}
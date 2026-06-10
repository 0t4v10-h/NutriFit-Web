package br.com.nutrifit.controller;

import br.com.nutrifit.model.Treino;
import br.com.nutrifit.model.Usuario;
import br.com.nutrifit.model.enums.PerfilUsuario;
import br.com.nutrifit.service.TreinoService;
import br.com.nutrifit.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/treinos")
public class TreinoController {

    private final TreinoService service;
    private final UsuarioService usuarioService;

    public TreinoController(
            TreinoService service,
            UsuarioService usuarioService) {

        this.service = service;
        this.usuarioService = usuarioService;
    }

    private String validarAdmin(HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");

        if (usuario == null) {
            return "redirect:/login";
        }

        if (usuario.getPerfil() != PerfilUsuario.ADMIN) {
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

        model.addAttribute(
                "treinos",
                service.listarTodos());

        return "treinos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model, HttpSession session) {

        String validacao = validarAdmin(session);

        if (validacao != null) {
            return validacao;
        }

        model.addAttribute(
                "treino",
                new Treino());

        model.addAttribute(
                "usuarios",
                usuarioService.listarTodos());

        return "treinos/form";
    }

    @PostMapping("/salvar")
    public String salvar(
            @Valid @ModelAttribute Treino treino,
            BindingResult result,
            Model model,
            HttpSession session) {

        String validacao = validarAdmin(session);

        if (validacao != null) {
            return validacao;
        }

        if (treino.getUsuario() == null
                || treino.getUsuario().getId() == null) {

            model.addAttribute(
                    "usuarios",
                    usuarioService.listarTodos());

            model.addAttribute(
                    "erroUsuario",
                    "Selecione um usuário.");

            return "treinos/form";
        }

        if (result.hasErrors()) {

            model.addAttribute(
                    "usuarios",
                    usuarioService.listarTodos());

            return "treinos/form";
        }

        treino.setUsuario(
                usuarioService.buscarPorId(
                        treino.getUsuario().getId()));

        service.salvar(treino);

        return "redirect:/treinos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id,
                         Model model,
                         HttpSession session) {

        String validacao = validarAdmin(session);

        if (validacao != null) {
            return validacao;
        }

        model.addAttribute(
                "treino",
                service.buscarPorId(id));

        model.addAttribute(
                "usuarios",
                usuarioService.listarTodos());

        return "treinos/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id,
                          HttpSession session) {

        String validacao = validarAdmin(session);

        if (validacao != null) {
            return validacao;
        }

        service.excluir(id);

        return "redirect:/treinos";
    }
}
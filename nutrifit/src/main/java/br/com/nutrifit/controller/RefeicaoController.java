package br.com.nutrifit.controller;

import br.com.nutrifit.model.Refeicao;
import br.com.nutrifit.model.Usuario;
import br.com.nutrifit.model.enums.PerfilUsuario;
import br.com.nutrifit.service.RefeicaoService;
import br.com.nutrifit.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/refeicoes")
public class RefeicaoController {

    private final RefeicaoService service;
    private final UsuarioService usuarioService;

    public RefeicaoController(RefeicaoService service,
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
                "refeicoes",
                service.listarTodos());

        return "refeicoes/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model, HttpSession session) {

        String validacao = validarAdmin(session);

        if (validacao != null) {
            return validacao;
        }

        Refeicao refeicao = new Refeicao();

        model.addAttribute("refeicao", refeicao);
        model.addAttribute(
                "usuarios",
                usuarioService.listarTodos());

        return "refeicoes/form";
    }

    @PostMapping("/salvar")
    public String salvar(
            @Valid @ModelAttribute Refeicao refeicao,
            BindingResult result,
            Model model,
            HttpSession session) {

        String validacao = validarAdmin(session);

        if (validacao != null) {
            return validacao;
        }

        if (refeicao.getUsuario() == null
                || refeicao.getUsuario().getId() == null) {

            model.addAttribute(
                    "usuarios",
                    usuarioService.listarTodos());

            model.addAttribute(
                    "erroUsuario",
                    "Selecione um usuário.");

            return "refeicoes/form";
        }

        if (result.hasErrors()) {

            model.addAttribute(
                    "usuarios",
                    usuarioService.listarTodos());

            return "refeicoes/form";
        }

        refeicao.setUsuario(
                usuarioService.buscarPorId(
                        refeicao.getUsuario().getId()));

        service.salvar(refeicao);

        return "redirect:/refeicoes";
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
                "refeicao",
                service.buscarPorId(id));

        model.addAttribute(
                "usuarios",
                usuarioService.listarTodos());

        return "refeicoes/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id,
                          HttpSession session) {

        String validacao = validarAdmin(session);

        if (validacao != null) {
            return validacao;
        }

        service.excluir(id);

        return "redirect:/refeicoes";
    }
}
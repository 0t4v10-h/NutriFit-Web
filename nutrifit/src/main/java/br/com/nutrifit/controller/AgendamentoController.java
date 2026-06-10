package br.com.nutrifit.controller;

import br.com.nutrifit.model.Agendamento;
import br.com.nutrifit.model.Usuario;
import br.com.nutrifit.model.enums.PerfilUsuario;
import br.com.nutrifit.service.AgendamentoService;
import br.com.nutrifit.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/agendamentos")
public class AgendamentoController {

    private final AgendamentoService service;
    private final UsuarioService usuarioService;

    public AgendamentoController(
            AgendamentoService service,
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
                "agendamentos",
                service.listarTodos());

        return "agendamentos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model, HttpSession session) {

        String validacao = validarAdmin(session);

        if (validacao != null) {
            return validacao;
        }

        model.addAttribute(
                "agendamento",
                new Agendamento());

        model.addAttribute(
                "usuarios",
                usuarioService.listarTodos());

        return "agendamentos/form";
    }

    @PostMapping("/salvar")
    public String salvar(
            @Valid @ModelAttribute Agendamento agendamento,
            BindingResult result,
            Model model,
            HttpSession session) {

        String validacao = validarAdmin(session);

        if (validacao != null) {
            return validacao;
        }

        if (agendamento.getUsuario() == null
                || agendamento.getUsuario().getId() == null) {

            model.addAttribute(
                    "usuarios",
                    usuarioService.listarTodos());

            model.addAttribute(
                    "erroUsuario",
                    "Selecione um usuário.");

            return "agendamentos/form";
        }

        if (result.hasErrors()) {

            model.addAttribute(
                    "usuarios",
                    usuarioService.listarTodos());

            return "agendamentos/form";
        }

        agendamento.setUsuario(
                usuarioService.buscarPorId(
                        agendamento.getUsuario().getId()));

        service.salvar(agendamento);

        return "redirect:/agendamentos";
    }

    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model,
            HttpSession session) {

        String validacao = validarAdmin(session);

        if (validacao != null) {
            return validacao;
        }

        model.addAttribute(
                "agendamento",
                service.buscarPorId(id));

        model.addAttribute(
                "usuarios",
                usuarioService.listarTodos());

        return "agendamentos/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(
            @PathVariable Long id,
            HttpSession session) {

        String validacao = validarAdmin(session);

        if (validacao != null) {
            return validacao;
        }

        service.excluir(id);

        return "redirect:/agendamentos";
    }
}
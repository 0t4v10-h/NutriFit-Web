package br.com.nutrifit.controller;

import br.com.nutrifit.model.Agendamento;
import br.com.nutrifit.service.AgendamentoService;
import br.com.nutrifit.service.UsuarioService;
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

    @GetMapping
    public String listar(Model model) {

        model.addAttribute(
                "agendamentos",
                service.listarTodos());

        return "agendamentos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {

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
            Model model) {

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
            Model model) {

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
            @PathVariable Long id) {

        service.excluir(id);

        return "redirect:/agendamentos";
    }
}
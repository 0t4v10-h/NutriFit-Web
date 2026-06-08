package br.com.nutrifit.controller;

import br.com.nutrifit.model.Refeicao;
import br.com.nutrifit.service.RefeicaoService;
import br.com.nutrifit.service.UsuarioService;
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

    @GetMapping
    public String listar(Model model) {

        model.addAttribute("refeicoes",
                service.listarTodos());

        return "refeicoes/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {

        Refeicao refeicao = new Refeicao();

        model.addAttribute("refeicao", refeicao);
        model.addAttribute("usuarios",
                usuarioService.listarTodos());

        return "refeicoes/form";
    }

    @PostMapping("/salvar")
    public String salvar(
            @Valid @ModelAttribute Refeicao refeicao,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {

            model.addAttribute("usuarios",
                    usuarioService.listarTodos());

            return "refeicoes/form";
        }

        service.salvar(refeicao);

        return "redirect:/refeicoes";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id,
                         Model model) {

        model.addAttribute("refeicao",
                service.buscarPorId(id));

        model.addAttribute("usuarios",
                usuarioService.listarTodos());

        return "refeicoes/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {

        service.excluir(id);

        return "redirect:/refeicoes";
    }
}
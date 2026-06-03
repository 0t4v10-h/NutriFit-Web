package br.com.nutrifit.controller;

import br.com.nutrifit.model.Meta;
import br.com.nutrifit.service.MetaService;
import br.com.nutrifit.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/metas")
public class MetaController {

    private final MetaService service;
    private final UsuarioService usuarioService;

    public MetaController(MetaService service,
                          UsuarioService usuarioService) {
        this.service = service;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("metas", service.listarTodos());
        return "metas/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {

        model.addAttribute("meta", new Meta());
        model.addAttribute("usuarios",
                usuarioService.listarTodos());

        return "metas/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Meta meta,
                         BindingResult result,
                         Model model) {

        if (result.hasErrors()) {

            model.addAttribute("usuarios",
                    usuarioService.listarTodos());

            return "metas/form";
        }

        service.salvar(meta);

        return "redirect:/metas";
    }
}
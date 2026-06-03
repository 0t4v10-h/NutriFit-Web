package br.com.nutrifit.controller;

import br.com.nutrifit.model.Meta;
import br.com.nutrifit.model.Usuario;
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

        Meta meta = new Meta();
        meta.setUsuario(new Usuario());

        model.addAttribute("meta", meta);
        model.addAttribute("usuarios",
                usuarioService.listarTodos());

        return "metas/form";
    }

    @PostMapping("/salvar")
    public String salvar(
            @Valid @ModelAttribute Meta meta,
            BindingResult result,
            Model model) {

        System.out.println(meta.getUsuario());

        if (result.hasErrors()) {

            model.addAttribute("usuarios",
                    usuarioService.listarTodos());

            return "metas/form";
        }

        if (meta.getUsuario() == null ||
                meta.getUsuario().getId() == null) {

            model.addAttribute("usuarios",
                    usuarioService.listarTodos());

            return "metas/form";
        }
        
        meta.setUsuario(
                usuarioService.buscarPorId(
                        meta.getUsuario().getId()
                )
        );

        service.salvar(meta);

        return "redirect:/metas";
    }
}
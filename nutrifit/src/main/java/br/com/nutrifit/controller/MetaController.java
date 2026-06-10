package br.com.nutrifit.controller;

import br.com.nutrifit.model.Meta;
import br.com.nutrifit.model.Usuario;
import br.com.nutrifit.model.enums.PerfilUsuario;
import br.com.nutrifit.service.MetaService;
import br.com.nutrifit.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
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

        model.addAttribute("metas", service.listarTodos());

        return "metas/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model, HttpSession session) {

        String validacao = validarAdmin(session);

        if (validacao != null) {
            return validacao;
        }

        Meta meta = new Meta();
        meta.setUsuario(new Usuario());

        model.addAttribute("meta", meta);
        model.addAttribute("usuarios", usuarioService.listarTodos());

        return "metas/form";
    }

    @PostMapping("/salvar")
    public String salvar(
            @Valid @ModelAttribute Meta meta,
            BindingResult result,
            Model model,
            HttpSession session) {

        String validacao = validarAdmin(session);

        if (validacao != null) {
            return validacao;
        }

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

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id,
                         Model model,
                         HttpSession session) {

        String validacao = validarAdmin(session);

        if (validacao != null) {
            return validacao;
        }

        Meta meta = service.buscarPorId(id);

        model.addAttribute("meta", meta);
        model.addAttribute("usuarios",
                usuarioService.listarTodos());

        return "metas/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id,
                          HttpSession session) {

        String validacao = validarAdmin(session);

        if (validacao != null) {
            return validacao;
        }

        service.excluir(id);

        return "redirect:/metas";
    }
}
package br.com.nutrifit.controller;

import br.com.nutrifit.model.Meta;
import br.com.nutrifit.model.Usuario;
import br.com.nutrifit.repository.AgendamentoRepository;
import br.com.nutrifit.repository.MetaRepository;
import br.com.nutrifit.repository.RefeicaoRepository;
import br.com.nutrifit.repository.TreinoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MeuPerfilController {

    private final MetaRepository metaRepository;
    private final RefeicaoRepository refeicaoRepository;
    private final TreinoRepository treinoRepository;
    private final AgendamentoRepository agendamentoRepository;

    public MeuPerfilController(
            MetaRepository metaRepository,
            RefeicaoRepository refeicaoRepository,
            TreinoRepository treinoRepository,
            AgendamentoRepository agendamentoRepository) {

        this.metaRepository = metaRepository;
        this.refeicaoRepository = refeicaoRepository;
        this.treinoRepository = treinoRepository;
        this.agendamentoRepository = agendamentoRepository;
    }

    @GetMapping("/minha-meta")
    public String minhaMeta(
            HttpSession session,
            Model model) {

        Usuario usuario =
                (Usuario) session.getAttribute("usuarioLogado");

        if (usuario == null) {
            return "redirect:/login";
        }

        Meta meta = metaRepository
                .findByUsuarioId(usuario.getId())
                .orElse(null);

        model.addAttribute("usuario", usuario);
        model.addAttribute("meta", meta);

        return "metas/minha-meta";
    }

    @GetMapping("/minhas-refeicoes")
    public String minhasRefeicoes(
            HttpSession session,
            Model model) {

        Usuario usuario =
                (Usuario) session.getAttribute("usuarioLogado");

        if (usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute(
                "refeicoes",
                refeicaoRepository.findByUsuarioId(usuario.getId()));

        return "refeicoes/minhas-refeicoes";
    }

    @GetMapping("/meus-treinos")
    public String meusTreinos(
            HttpSession session,
            Model model) {

        Usuario usuario =
                (Usuario) session.getAttribute("usuarioLogado");

        if (usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute(
                "treinos",
                treinoRepository.findByUsuarioId(usuario.getId()));

        return "treinos/meus-treinos";
    }

    @GetMapping("/meus-agendamentos")
    public String meusAgendamentos(
            HttpSession session,
            Model model) {

        Usuario usuario =
                (Usuario) session.getAttribute("usuarioLogado");

        if (usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute(
                "agendamentos",
                agendamentoRepository.findByUsuarioId(
                        usuario.getId()));

        return "agendamentos/meus-agendamentos";
    }
}
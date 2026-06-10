package br.com.nutrifit.controller;

import br.com.nutrifit.model.Usuario;
import br.com.nutrifit.model.enums.PerfilUsuario;
import br.com.nutrifit.repository.UsuarioRepository;
import br.com.nutrifit.repository.MetaRepository;
import br.com.nutrifit.repository.RefeicaoRepository;
import br.com.nutrifit.repository.TreinoRepository;
import br.com.nutrifit.repository.AgendamentoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminDashboardController {

    private final UsuarioRepository usuarioRepository;
    private final MetaRepository metaRepository;
    private final RefeicaoRepository refeicaoRepository;
    private final TreinoRepository treinoRepository;
    private final AgendamentoRepository agendamentoRepository;

    public AdminDashboardController(
            UsuarioRepository usuarioRepository,
            MetaRepository metaRepository,
            RefeicaoRepository refeicaoRepository,
            TreinoRepository treinoRepository,
            AgendamentoRepository agendamentoRepository) {

        this.usuarioRepository = usuarioRepository;
        this.metaRepository = metaRepository;
        this.refeicaoRepository = refeicaoRepository;
        this.treinoRepository = treinoRepository;
        this.agendamentoRepository = agendamentoRepository;
    }

    @GetMapping("/admin/dashboard")
    public String dashboardAdmin(
            HttpSession session,
            Model model) {

        Usuario usuario =
                (Usuario) session.getAttribute("usuarioLogado");

        if (usuario == null) {
            return "redirect:/login";
        }

        if (usuario.getPerfil() != PerfilUsuario.ADMIN) {
            return "redirect:/dashboard";
        }

        model.addAttribute(
                "totalUsuarios",
                usuarioRepository.count());

        model.addAttribute(
                "totalMetas",
                metaRepository.count());

        model.addAttribute(
                "totalRefeicoes",
                refeicaoRepository.count());

        model.addAttribute(
                "totalTreinos",
                treinoRepository.count());

        model.addAttribute(
                "totalAgendamentos",
                agendamentoRepository.count());

        return "dashboard-admin";
    }
}
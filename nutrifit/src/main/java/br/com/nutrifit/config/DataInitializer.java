package br.com.nutrifit.config;

import br.com.nutrifit.model.Usuario;
import br.com.nutrifit.model.enums.ObjetivoNutricional;
import br.com.nutrifit.model.enums.PerfilUsuario;
import br.com.nutrifit.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;

    public DataInitializer(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(String... args) {

        if (usuarioRepository.findByEmail("admin@nutrifit.com").isEmpty()) {

            Usuario admin = new Usuario();

            admin.setNome("Administrador");
            admin.setEmail("admin@nutrifit.com");
            admin.setSenha("123456");

            admin.setPeso(70.0);
            admin.setAltura(1.70);
            admin.setMetaCalorica(2000.0);

            admin.setObjetivo(
                    ObjetivoNutricional.MANTER_PESO);

            admin.setPerfil(
                    PerfilUsuario.ADMIN);

            usuarioRepository.save(admin);

            System.out.println(
                    "Administrador criado com sucesso!");
        }
    }
}
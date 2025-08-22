package sistema_clinica;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sistema_clinica.model.TipoUsuario;
import sistema_clinica.model.Usuario;
import sistema_clinica.repository.UsuarioRepository;

import java.util.Arrays;
import java.util.List;


@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        if (usuarioRepository.count() == 0) {
            System.out.println(">>> Banco de dados vazio. Populando com dados iniciais...");

            criarUsuariosIniciais();

            System.out.println(">>> Dados iniciais inseridos com sucesso!");
        } else {
            System.out.println(">>> Banco de dados já contém dados. Nenhuma ação necessária.");
        }
    }

    private void criarUsuariosIniciais() {
        Usuario admin = new Usuario();
        admin.setNome("Administrador do Sistema");
        admin.setUsername("admin");
        admin.setEmail("admin@clinica.com");
        admin.setSenha(passwordEncoder.encode("admin123"));
        admin.setTelefone("79999990001");
        admin.setTipoUsuario(TipoUsuario.ADMIN);

        Usuario medico = new Usuario();
        medico.setNome("Dr. Carlos Andrade");
        medico.setUsername("carlos.med");
        medico.setEmail("carlos.med@clinica.com");
        medico.setSenha(passwordEncoder.encode("medico123"));
        medico.setTelefone("79999990002");
        medico.setTipoUsuario(TipoUsuario.MEDICO);

        Usuario paciente = new Usuario();
        paciente.setNome("Ana Paula");
        paciente.setUsername("ana.paula");
        paciente.setEmail("anapaula@email.com");
        paciente.setSenha(passwordEncoder.encode("paciente123"));
        paciente.setTelefone("79999990003");
        paciente.setTipoUsuario(TipoUsuario.PACIENTE);

        Usuario recepcionista = new Usuario();
        recepcionista.setNome("Recepcionista Principal");
        recepcionista.setUsername("recepcao");
        recepcionista.setEmail("recepcao@clinica.com");
        recepcionista.setSenha(passwordEncoder.encode("recepcao123"));
        recepcionista.setTelefone("79999990004");
        recepcionista.setTipoUsuario(TipoUsuario.RECEPCIONISTA);

        List<Usuario> usuarios = Arrays.asList(admin, medico, paciente, recepcionista);
        usuarioRepository.saveAll(usuarios);
    }
}

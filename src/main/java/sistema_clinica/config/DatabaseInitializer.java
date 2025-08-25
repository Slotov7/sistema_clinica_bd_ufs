package sistema_clinica.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sistema_clinica.model.TipoUsuario;
import sistema_clinica.model.relacional.Especialidade;
import sistema_clinica.model.relacional.Usuario;
import sistema_clinica.repository.relacional.EspecialidadeRepository;
import sistema_clinica.repository.relacional.UsuarioRepository;

import java.util.Arrays;
import java.util.List;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final EspecialidadeRepository especialidadeRepository;

    public DatabaseInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, EspecialidadeRepository especialidadeRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.especialidadeRepository = especialidadeRepository;
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
        if (especialidadeRepository.count() == 0) {
            System.out.println(">>> Populando banco de dados relacional com especialidades iniciais...");
            criarEspecialidadesIniciais();
            System.out.println(">>> Especialidades inseridas com sucesso!");
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

    private void criarEspecialidadesIniciais() {
        Especialidade cardiologia = new Especialidade();
        cardiologia.setNome("Cardiologia");
        cardiologia.setDescricao("Especialidade que cuida de doenças do coração e do sistema circulatório.");
        cardiologia.setCodCbo("225125");

        Especialidade dermatologia = new Especialidade();
        dermatologia.setNome("Dermatologia");
        dermatologia.setDescricao("Especialidade focada no diagnóstico e tratamento de doenças da pele, cabelos e unhas.");
        dermatologia.setCodCbo("225135");

        Especialidade ortopedia = new Especialidade();
        ortopedia.setNome("Ortopedia");
        ortopedia.setDescricao("Tratamento de lesões e doenças do sistema locomotor, como ossos, músculos e articulações.");
        ortopedia.setCodCbo("225285");

        Especialidade pediatria = new Especialidade();
        pediatria.setNome("Pediatria");
        pediatria.setDescricao("Cuidado da saúde de crianças e adolescentes.");
        pediatria.setCodCbo("225170");

        List<Especialidade> especialidades = Arrays.asList(cardiologia, dermatologia, ortopedia, pediatria);
        especialidadeRepository.saveAll(especialidades);
    }
}

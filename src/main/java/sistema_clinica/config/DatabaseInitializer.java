package sistema_clinica.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sistema_clinica.model.TipoUsuario;
import sistema_clinica.model.relacional.Especialidade;
import sistema_clinica.model.relacional.Especializado;
import sistema_clinica.model.relacional.EspecializadoId;
import sistema_clinica.model.relacional.Medico;
import sistema_clinica.model.relacional.Usuario;
import sistema_clinica.repository.relacional.EspecialidadeRepository;
import sistema_clinica.repository.relacional.EspecializadoRepository;
import sistema_clinica.repository.relacional.MedicoRepository;
import sistema_clinica.repository.relacional.UsuarioRepository;

import java.util.Arrays;
import java.util.List;

@Component
public class DatabaseInitializer implements CommandLineRunner {
    private final UsuarioRepository usuarioRepository;
    private final EspecialidadeRepository especialidadeRepository;
    private final MedicoRepository medicoRepository;
    private final EspecializadoRepository especializadoRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseInitializer(UsuarioRepository usuarioRepository,
                               EspecialidadeRepository especialidadeRepository,
                               MedicoRepository medicoRepository,
                               EspecializadoRepository especializadoRepository,
                               PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.especialidadeRepository = especialidadeRepository;
        this.medicoRepository = medicoRepository;
        this.especializadoRepository = especializadoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() == 0) {
            System.out.println(">>> Populando com usuários iniciais...");
            criarUsuariosIniciais();
        }
        if (especialidadeRepository.count() == 0) {
            System.out.println(">>> Populando com especialidades iniciais...");
            criarEspecialidadesIniciais();
        }
        if (medicoRepository.count() == 0) {
            System.out.println(">>> Populando com médicos iniciais...");
            criarMedicosIniciais();
        }
        if (especializadoRepository.count() == 0) {
            System.out.println(">>> Populando com relacionamentos (Especializado) iniciais...");
            criarRelacionamentosIniciais();
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



        List<Usuario> usuarios = Arrays.asList(admin, medico);
        usuarioRepository.saveAll(usuarios);
    }

    private void criarEspecialidadesIniciais() {
        Especialidade cardiologia = new Especialidade(null, "Cardiologia", "Cuida de doenças do coração.", "225125");
        Especialidade dermatologia = new Especialidade(null, "Dermatologia", "Cuida de doenças da pele.", "225135");

        List<Especialidade> especialidades = Arrays.asList(cardiologia, dermatologia);
        especialidadeRepository.saveAll(especialidades);
    }

    private void criarMedicosIniciais() {
        Usuario usuarioMedico = usuarioRepository.findByUsername("carlos.med").orElseThrow();

        Medico medicoCarlos = new Medico();
        medicoCarlos.setUsuario(usuarioMedico);
        medicoCarlos.setCrm(12345);

        medicoRepository.save(medicoCarlos);
    }

    private void criarRelacionamentosIniciais() {
        Medico medicoCarlos = medicoRepository.findById(usuarioRepository.findByUsername("carlos.med").orElseThrow().getId()).orElseThrow();
        Especialidade cardiologia = especialidadeRepository.findById(1).orElseThrow();
        Especialidade dermatologia = especialidadeRepository.findById(2).orElseThrow();

        EspecializadoId rel1Id = new EspecializadoId(Math.toIntExact(medicoCarlos.getId()), Math.toIntExact(cardiologia.getId()));
        Especializado rel1 = new Especializado(rel1Id, medicoCarlos, cardiologia);

        EspecializadoId rel2Id = new EspecializadoId(Math.toIntExact(medicoCarlos.getId()), Math.toIntExact(dermatologia.getId()));
        Especializado rel2 = new Especializado(rel2Id, medicoCarlos, dermatologia);

        List<Especializado> relacionamentos = Arrays.asList(rel1, rel2);
        especializadoRepository.saveAll(relacionamentos);
    }
}

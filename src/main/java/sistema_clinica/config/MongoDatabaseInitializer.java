package sistema_clinica.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sistema_clinica.model.TipoUsuario;
import sistema_clinica.model.mongo.EspecialidadeDocument;
import sistema_clinica.model.mongo.UsuarioDocument;
import sistema_clinica.repository.mongo.EspecialidadeMongoRepository;
import sistema_clinica.repository.mongo.UsuarioMongoRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MongoDatabaseInitializer implements CommandLineRunner {

    private final UsuarioMongoRepository usuarioMongoRepository;
    private final EspecialidadeMongoRepository especialidadeMongoRepository;
    private final PasswordEncoder passwordEncoder;

    public MongoDatabaseInitializer(UsuarioMongoRepository usuarioMongoRepository,
                                    EspecialidadeMongoRepository especialidadeMongoRepository,
                                    PasswordEncoder passwordEncoder) {
        this.usuarioMongoRepository = usuarioMongoRepository;
        this.especialidadeMongoRepository = especialidadeMongoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (usuarioMongoRepository.count() == 0) {
            System.out.println(">>> Populando banco de dados MongoDB com usuários iniciais...");
            criarUsuariosIniciais();
        }

        if (especialidadeMongoRepository.count() == 0) {
            System.out.println(">>> Populando banco de dados MongoDB com especialidades iniciais e associando ao médico...");
            criarEspecialidadesEAssociarMedico();
        }

        
    }

    private void criarUsuariosIniciais() {
        UsuarioDocument admin = new UsuarioDocument();
        admin.setNome("Admin MongoDB");
        admin.setUsername("admin_mongo");
        admin.setEmail("admin@mongo.com");
        admin.setSenha(passwordEncoder.encode("mongo123"));
        admin.setTipoUsuario(TipoUsuario.ADMIN);

        UsuarioDocument medico = new UsuarioDocument();
        medico.setNome("Dr. Carlos MongoDB");
        medico.setUsername("carlos.med.mongo");
        medico.setEmail("carlos.med@mongo.com");
        medico.setSenha(passwordEncoder.encode("mongo123"));
        medico.setTipoUsuario(TipoUsuario.MEDICO);
        medico.setCrm("12345-SE");

        // NOVO USUÁRIO - PACIENTE
        UsuarioDocument paciente = new UsuarioDocument();
        paciente.setNome("Ana Paciente");
        paciente.setUsername("ana.paciente");
        paciente.setEmail("ana.paciente@email.com");
        paciente.setSenha(passwordEncoder.encode("paciente123"));
        paciente.setTipoUsuario(TipoUsuario.PACIENTE);

        // NOVO USUÁRIO - RECEPCIONISTA
        UsuarioDocument recepcionista = new UsuarioDocument();
        recepcionista.setNome("Bruno Recepcionista");
        recepcionista.setUsername("bruno.recep");
        recepcionista.setEmail("bruno.recep@clinica.com");
        recepcionista.setSenha(passwordEncoder.encode("recep123"));
        recepcionista.setTipoUsuario(TipoUsuario.RECEPCIONISTA);

        // Adicionando TODOS os usuários à lista para salvar de uma vez
        List<UsuarioDocument> usuarios = Arrays.asList(admin, medico, paciente, recepcionista);
        usuarioMongoRepository.saveAll(usuarios);
    }

    private void criarEspecialidadesEAssociarMedico() {
        EspecialidadeDocument cardiologia = new EspecialidadeDocument();
        cardiologia.setNome("Cardiologia");
        cardiologia.setDescricao("Cuida de doenças do coração.");
        cardiologia.setCodCbo("225125");

        EspecialidadeDocument dermatologia = new EspecialidadeDocument();
        dermatologia.setNome("Dermatologia");
        dermatologia.setDescricao("Cuida de doenças da pele.");
        dermatologia.setCodCbo("225135");

        List<EspecialidadeDocument> especialidadesSalvas = especialidadeMongoRepository.saveAll(Arrays.asList(cardiologia, dermatologia));

        UsuarioDocument medicoCarlos = usuarioMongoRepository.findByUsername("carlos.med.mongo")
                .orElseThrow(() -> new RuntimeException("Erro: Médico 'carlos.med.mongo' não encontrado."));

        List<String> idsDasEspecialidades = especialidadesSalvas.stream()
                .map(EspecialidadeDocument::getId)
                .collect(Collectors.toList());

        medicoCarlos.setEspecialidadeIds(idsDasEspecialidades);
        usuarioMongoRepository.save(medicoCarlos);

        System.out.println(">>> Médico " + medicoCarlos.getNome() + " associado às especialidades: " +
                especialidadesSalvas.stream().map(EspecialidadeDocument::getNome).collect(Collectors.joining(", ")));
    }
}
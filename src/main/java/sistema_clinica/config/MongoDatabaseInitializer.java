package sistema_clinica.config;


import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sistema_clinica.model.TipoUsuario;
import sistema_clinica.model.mongo.UsuarioDocument;
import sistema_clinica.repository.mongo.UsuarioMongoRepository;

import java.util.Arrays;
import java.util.List;

@Component
public class MongoDatabaseInitializer implements CommandLineRunner {

    private final UsuarioMongoRepository usuarioMongoRepository;
    private final PasswordEncoder passwordEncoder;

    public MongoDatabaseInitializer(UsuarioMongoRepository usuarioMongoRepository, PasswordEncoder passwordEncoder) {
        this.usuarioMongoRepository = usuarioMongoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        usuarioMongoRepository.deleteAll();

        System.out.println(">>> Populando banco de dados MongoDB com dados iniciais...");
        criarUsuariosIniciais();
        System.out.println(">>> Dados iniciais inseridos com sucesso no MongoDB!");
    }

    private void criarUsuariosIniciais() {
        UsuarioDocument admin = new UsuarioDocument();
        admin.setNome("Admin MongoDB");
        admin.setUsername("admin_mongo");
        admin.setEmail("admin@mongo.com");
        admin.setSenha(passwordEncoder.encode("mongo123"));
        admin.setTipoUsuario(TipoUsuario.ADMIN);

        UsuarioDocument medico = new UsuarioDocument();
        medico.setNome("Dr. Mongo");
        medico.setUsername("medico_mongo");
        medico.setEmail("medico@mongo.com");
        medico.setSenha(passwordEncoder.encode("mongo123"));
        medico.setTipoUsuario(TipoUsuario.MEDICO);

        UsuarioDocument paciente = new UsuarioDocument();
        paciente.setNome("Paciente MongoDB");
        paciente.setUsername("paciente_mongo");
        paciente.setEmail("paciente@mongo.com");
        paciente.setSenha(passwordEncoder.encode("mongo123"));
        paciente.setTipoUsuario(TipoUsuario.PACIENTE);

        UsuarioDocument recepcionista = new UsuarioDocument();
        recepcionista.setNome("Recepcionista MongoDB");
        recepcionista.setUsername("recepcionista_mongo");
        recepcionista.setEmail("recepcionista@mongo.com");
        recepcionista.setSenha(passwordEncoder.encode("mongo123"));
        recepcionista.setTipoUsuario(TipoUsuario.RECEPCIONISTA);

        List<UsuarioDocument> usuarios = Arrays.asList(admin, medico, paciente, recepcionista);
        usuarioMongoRepository.saveAll(usuarios);
    }
}

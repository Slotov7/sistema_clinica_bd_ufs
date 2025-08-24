package sistema_clinica.service.mongo;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sistema_clinica.dto.UsuarioRequestDTO;
import sistema_clinica.model.mongo.UsuarioDocument;
import sistema_clinica.repository.mongo.UsuarioMongoRepository;

import java.util.List;


@Service
public class UsuarioMongoService {

    private final UsuarioMongoRepository usuarioMongoRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioMongoService(UsuarioMongoRepository usuarioMongoRepository, PasswordEncoder passwordEncoder) {
        this.usuarioMongoRepository = usuarioMongoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UsuarioDocument> listarTodos() {
        return usuarioMongoRepository.findAll();
    }

    public UsuarioDocument buscarPorId(String id) {
        return usuarioMongoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com id: " + id + " não encontrado.")); // Exceção alterada
    }

    public UsuarioDocument criarUsuario(UsuarioRequestDTO dto) {
        UsuarioDocument novoUsuario = new UsuarioDocument();
        novoUsuario.setNome(dto.getNome());
        novoUsuario.setUsername(dto.getUsername());
        novoUsuario.setEmail(dto.getEmail());
        novoUsuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        novoUsuario.setTelefone(dto.getTelefone());
        novoUsuario.setTipoUsuario(dto.getTipoUsuario());
        return usuarioMongoRepository.save(novoUsuario);
    }

    public UsuarioDocument atualizarUsuario(String id, UsuarioRequestDTO dto) {
        UsuarioDocument usuarioExistente = buscarPorId(id);

        usuarioExistente.setNome(dto.getNome());
        usuarioExistente.setUsername(dto.getUsername());
        usuarioExistente.setEmail(dto.getEmail());
        usuarioExistente.setTelefone(dto.getTelefone());
        usuarioExistente.setTipoUsuario(dto.getTipoUsuario());

        return usuarioMongoRepository.save(usuarioExistente);
    }

    public void deletarUsuario(String id) {
        if (!usuarioMongoRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário com id: " + id + " não encontrado para exclusão.");
        }
        usuarioMongoRepository.deleteById(id);
    }
}
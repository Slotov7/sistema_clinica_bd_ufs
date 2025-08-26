package sistema_clinica.service.mongo;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sistema_clinica.dto.UsuarioRequestDTO;
import sistema_clinica.model.TipoUsuario;
import sistema_clinica.model.mongo.EspecialidadeDocument;
import sistema_clinica.model.mongo.UsuarioDocument;
import sistema_clinica.repository.mongo.UsuarioMongoRepository;

import java.util.List;


@Service
public class UsuarioMongoService {

    private final UsuarioMongoRepository usuarioMongoRepository;
    private final PasswordEncoder passwordEncoder;
    private final EspecialidadeMongoService especialidadeMongoService;

    public UsuarioMongoService(UsuarioMongoRepository usuarioMongoRepository, PasswordEncoder passwordEncoder, EspecialidadeMongoService especialidadeMongoService) {
        this.usuarioMongoRepository = usuarioMongoRepository;
        this.passwordEncoder = passwordEncoder;
        this.especialidadeMongoService = especialidadeMongoService;
    }

    public List<UsuarioDocument> listarTodos() {
        return usuarioMongoRepository.findAll();
    }

    public UsuarioDocument buscarPorId(String id) {
        return usuarioMongoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com id: " + id + " não encontrado."));
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
        usuarioExistente.setSenha(passwordEncoder.encode(dto.getSenha()));
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
    public UsuarioDocument adicionarEspecialidade(String usuarioId, String especialidadeId) {
        UsuarioDocument usuario = buscarPorId(usuarioId);
        if (usuario.getTipoUsuario() != TipoUsuario.MEDICO) {
            throw new IllegalArgumentException("Só é possível adicionar especialidades a usuários do tipo MÉDICO.");
        }
        EspecialidadeDocument especialidade = especialidadeMongoService.buscarPorId(especialidadeId);
        if (usuario.getEspecialidadeIds().contains(especialidade.getId())) {
            throw new IllegalArgumentException("O médico já possui esta especialidade.");
        }
        usuario.getEspecialidadeIds().add(especialidade.getId());
        return usuarioMongoRepository.save(usuario);
    }
    public UsuarioDocument removerEspecialidade(String usuarioId, String especialidadeId) {
        UsuarioDocument usuario = buscarPorId(usuarioId);
        if (usuario.getTipoUsuario() != TipoUsuario.MEDICO) {
            throw new IllegalArgumentException("Apenas usuários do tipo MÉDICO possuem especialidades para remover.");
        }
        boolean removeu = usuario.getEspecialidadeIds().remove(especialidadeId);
        if (!removeu) {
            throw new EntityNotFoundException("O médico não possui a especialidade com id: " + especialidadeId + " para ser removida.");
        }
        return usuarioMongoRepository.save(usuario);
    }
}
package sistema_clinica.service.relacional;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sistema_clinica.dto.UsuarioRequestDTO;
import sistema_clinica.model.TipoUsuario;
import sistema_clinica.model.relacional.Usuario;
import sistema_clinica.repository.relacional.MedicoRepository;
import sistema_clinica.repository.relacional.UsuarioRepository;

import java.util.List;

@Service
public class UsuarioRelacionalService {
    private final UsuarioRepository usuarioRepository;
    private final MedicoRepository medicoRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioRelacionalService(UsuarioRepository usuarioRepository,
                                    MedicoRepository medicoRepository,
                                    PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.medicoRepository = medicoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com id: " + id + " não encontrado."));
    }

    public Usuario criarUsuario(UsuarioRequestDTO dto) {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(dto.getNome());
        novoUsuario.setUsername(dto.getUsername());
        novoUsuario.setEmail(dto.getEmail());
        novoUsuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        novoUsuario.setTelefone(dto.getTelefone());
        novoUsuario.setTipoUsuario(dto.getTipoUsuario());
        return usuarioRepository.save(novoUsuario);
    }

    public Usuario atualizarUsuario(Integer id, UsuarioRequestDTO dto) {
        Usuario usuarioExistente = buscarPorId(id);

        usuarioExistente.setNome(dto.getNome());
        usuarioExistente.setUsername(dto.getUsername());
        usuarioExistente.setEmail(dto.getEmail());
        usuarioExistente.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuarioExistente.setTelefone(dto.getTelefone());
        usuarioExistente.setTipoUsuario(dto.getTipoUsuario());

        return usuarioRepository.save(usuarioExistente);
    }

    @Transactional
    public void deletarUsuario(Integer id) {
        Usuario usuario = this.buscarPorId(id);

        if (usuario.getTipoUsuario() == TipoUsuario.MEDICO && medicoRepository.existsById(id)) {
            throw new UnsupportedOperationException(
                    "Este usuário é um médico e possui dependências. Utilize o endpoint /api/rel/medicos/{id} para removê-lo."
            );
        }

        usuarioRepository.deleteById(id);
    }
}

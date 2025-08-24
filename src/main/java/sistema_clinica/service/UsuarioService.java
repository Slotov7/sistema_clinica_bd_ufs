package sistema_clinica.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sistema_clinica.dto.UsuarioRequestDTO;
import sistema_clinica.model.relacional.Usuario;
import sistema_clinica.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(int id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com id: " + id + " não encontrado."));
    }

    public Usuario criarUsuario(UsuarioRequestDTO dto) {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(dto.getNome());
        novoUsuario.setUsername(dto.getUsername());
        novoUsuario.setEmail(dto.getEmail());
        novoUsuario.setSenha(passwordEncoder.encode(dto.getSenha())); // Criptografa a senha
        novoUsuario.setTelefone(dto.getTelefone());
        novoUsuario.setTipoUsuario(dto.getTipoUsuario());
        return usuarioRepository.save(novoUsuario);
    }

    public Usuario atualizarUsuario(int id, UsuarioRequestDTO dto) {
        Usuario usuarioExistente = buscarPorId(id); // Reutiliza o método de busca

        usuarioExistente.setNome(dto.getNome());
        usuarioExistente.setUsername(dto.getUsername());
        usuarioExistente.setEmail(dto.getEmail());
        usuarioExistente.setTelefone(dto.getTelefone());
        usuarioExistente.setTipoUsuario(dto.getTipoUsuario());

        return usuarioRepository.save(usuarioExistente);
    }

    public void deletarUsuario(int id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário com id: " + id + " não encontrado para exclusão.");
        }
        usuarioRepository.deleteById(id);
    }
}
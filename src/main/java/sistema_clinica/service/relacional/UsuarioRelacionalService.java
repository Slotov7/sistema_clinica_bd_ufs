package sistema_clinica.service.relacional;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sistema_clinica.dto.UsuarioRequestDTO;
import sistema_clinica.model.relacional.Usuario;
import sistema_clinica.repository.relacional.UsuarioRepository;

import java.util.List;

/**
 * Serviço que contém a lógica de negócio para as operações CRUD de Usuários
 * no banco de dados relacional (PostgreSQL).
 */
@Service
public class UsuarioRelacionalService { // Nome da classe alterado

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioRelacionalService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Integer id) { // ID é Integer
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

    public Usuario atualizarUsuario(Integer id, UsuarioRequestDTO dto) { // ID é Integer
        Usuario usuarioExistente = buscarPorId(id);

        usuarioExistente.setNome(dto.getNome());
        usuarioExistente.setUsername(dto.getUsername());
        usuarioExistente.setEmail(dto.getEmail());
        usuarioExistente.setTelefone(dto.getTelefone());
        usuarioExistente.setTipoUsuario(dto.getTipoUsuario());
        // A senha geralmente não é atualizada aqui, a menos que seja uma funcionalidade específica

        return usuarioRepository.save(usuarioExistente);
    }

    public void deletarUsuario(Integer id) { // ID é Integer
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário com id: " + id + " não encontrado para exclusão.");
        }
        usuarioRepository.deleteById(id);
    }
}
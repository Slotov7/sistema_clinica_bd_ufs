package sistema_clinica.controller.relacional;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema_clinica.dto.UsuarioRequestDTO;
import sistema_clinica.dto.UsuarioResponseDTO;
import sistema_clinica.model.relacional.Usuario;
import sistema_clinica.service.relacional.UsuarioRelacionalService; // Importa o serviço específico

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador para gerenciar as operações CRUD de Usuários
 * no banco de dados relacional (PostgreSQL).
 */
@RestController
@RequestMapping("/api/rel/usuarios") // Rota base para os endpoints relacionais
public class UsuarioRelacionalController {

    private final UsuarioRelacionalService usuarioService; // Injeta o serviço relacional

    public UsuarioRelacionalController(UsuarioRelacionalService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        List<UsuarioResponseDTO> responseDTOs = usuarios.stream()
                .map(UsuarioResponseDTO::new) // O DTO sabe como se construir a partir de um Usuario
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuarioPorId(@PathVariable Integer id) { // ID é Integer
        Usuario usuario = usuarioService.buscarPorId(id);
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO(usuario);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(@Valid @RequestBody UsuarioRequestDTO dto) {
        Usuario usuarioSalvo = usuarioService.criarUsuario(dto);
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO(usuarioSalvo);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@PathVariable Integer id, @Valid @RequestBody UsuarioRequestDTO dto) { // ID é Integer
        Usuario usuarioAtualizado = usuarioService.atualizarUsuario(id, dto);
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO(usuarioAtualizado);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Integer id) { // ID é Integer
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
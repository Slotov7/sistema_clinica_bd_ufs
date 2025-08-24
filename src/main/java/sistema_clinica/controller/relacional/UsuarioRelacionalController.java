package sistema_clinica.controller.relacional;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema_clinica.dto.UsuarioRequestDTO;
import sistema_clinica.dto.UsuarioResponseDTO;
import sistema_clinica.model.relacional.Usuario;
import sistema_clinica.service.relacional.UsuarioRelacionalService;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/rel/usuarios")
public class UsuarioRelacionalController {

    private final UsuarioRelacionalService usuarioService;

    public UsuarioRelacionalController(UsuarioRelacionalService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        List<UsuarioResponseDTO> responseDTOs = usuarios.stream()
                .map(UsuarioResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuarioPorId(@PathVariable Integer id) {
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
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@PathVariable Integer id, @Valid @RequestBody UsuarioRequestDTO dto) {
        Usuario usuarioAtualizado = usuarioService.atualizarUsuario(id, dto);
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO(usuarioAtualizado);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Integer id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
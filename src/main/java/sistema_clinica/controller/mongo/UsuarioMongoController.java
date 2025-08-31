package sistema_clinica.controller.mongo;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema_clinica.dto.AdicionarEspecialidadeRequestDTO;
import sistema_clinica.dto.UsuarioRequestDTO;
import sistema_clinica.dto.UsuarioResponseDTO;
import sistema_clinica.model.mongo.UsuarioDocument;
import sistema_clinica.service.mongo.UsuarioMongoService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/nosql/usuarios")
public class UsuarioMongoController {

    private final UsuarioMongoService usuarioMongoService;

    public UsuarioMongoController(UsuarioMongoService usuarioMongoService) {
        this.usuarioMongoService = usuarioMongoService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        List<UsuarioDocument> usuarios = usuarioMongoService.listarTodos();
        List<UsuarioResponseDTO> responseDTOs = usuarios.stream()
                .map(UsuarioResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuarioPorId(@PathVariable String id) {
        UsuarioDocument usuario = usuarioMongoService.buscarPorId(id);
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO(usuario);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(@Valid @RequestBody UsuarioRequestDTO dto) {
        UsuarioDocument usuarioSalvo = usuarioMongoService.criarUsuario(dto);
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO(usuarioSalvo);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@PathVariable String id, @Valid @RequestBody UsuarioRequestDTO dto) {
        UsuarioDocument usuarioAtualizado = usuarioMongoService.atualizarUsuario(id, dto);
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO(usuarioAtualizado);
        return ResponseEntity.ok(responseDTO);
    }

        @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable String id) {
        usuarioMongoService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}

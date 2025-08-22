package sistema_clinica.controller;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema_clinica.dto.UsuarioRequestDTO;
import sistema_clinica.dto.UsuarioResponseDTO;
import sistema_clinica.model.Usuario;
import sistema_clinica.repository.UsuarioRepository;
import sistema_clinica.service.UsuarioService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    public final UsuarioRepository usuarioRepository;
    public final UsuarioService usuarioService;

    public UsuarioController(UsuarioRepository usuarioRepository, UsuarioService usuarioService) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarTodos();

        List<UsuarioResponseDTO> responseDTOs = usuarios.stream()
                .map(UsuarioResponseDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuarioPorId(@PathVariable int id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO(usuario);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(@Valid @RequestBody UsuarioRequestDTO dto) {
        Usuario usuarioSalvo = usuarioService.criarUsuario(dto);
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO(usuarioSalvo);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@PathVariable int id, @Valid @RequestBody UsuarioRequestDTO dto) {
        Usuario usuarioAtualizado = usuarioService.atualizarUsuario(id, dto);
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO(usuarioAtualizado);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable int id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}

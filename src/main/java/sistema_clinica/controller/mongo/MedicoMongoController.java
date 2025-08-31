
package sistema_clinica.controller.mongo;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema_clinica.dto.AdicionarEspecialidadeRequestDTO;
import sistema_clinica.dto.MedicoDTO;
import sistema_clinica.dto.UsuarioResponseDTO;
import sistema_clinica.model.mongo.UsuarioDocument;
import sistema_clinica.service.mongo.MedicoMongoService;

import java.util.List;

@RestController
@RequestMapping("/api/nosql/medicos")
public class MedicoMongoController {

    private final MedicoMongoService medicoService;

    public MedicoMongoController(MedicoMongoService medicoService) {
        this.medicoService = medicoService;
    }


    @GetMapping
    public ResponseEntity<List<MedicoDTO>> listarMedicos() {
        return ResponseEntity.ok(medicoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicoDTO> buscarMedicoPorId(@PathVariable String id) {
        MedicoDTO medico = medicoService.buscarPorId(id);
        return ResponseEntity.ok(medico);
    }

    @PostMapping("/{medicoId}/especialidades")
    public ResponseEntity<UsuarioResponseDTO> adicionarEspecialidade(
            @PathVariable String medicoId,
            @Valid @RequestBody AdicionarEspecialidadeRequestDTO dto) {

        UsuarioDocument usuarioAtualizado = medicoService.adicionarEspecialidade(medicoId, dto.getEspecialidadeId());
        return ResponseEntity.ok(new UsuarioResponseDTO(usuarioAtualizado));
    }

    @DeleteMapping("/{medicoId}/especialidades/{especialidadeId}")
    public ResponseEntity<UsuarioResponseDTO> removerEspecialidade(
            @PathVariable String medicoId,
            @PathVariable String especialidadeId) {

        UsuarioDocument usuarioAtualizado = medicoService.removerEspecialidade(medicoId, especialidadeId);
        return ResponseEntity.ok(new UsuarioResponseDTO(usuarioAtualizado));
    }
}

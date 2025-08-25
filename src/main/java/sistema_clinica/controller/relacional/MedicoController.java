package sistema_clinica.controller.relacional;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema_clinica.dto.MedicoDTO;
import sistema_clinica.service.relacional.MedicoService;

import java.util.List;

@RestController
@RequestMapping("/api/rel/medicos")
public class MedicoController {

    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @PostMapping
    public ResponseEntity<MedicoDTO> criarMedico(@Valid @RequestBody MedicoDTO dto) {
        MedicoDTO medicoCriado = medicoService.criar(dto);
        return new ResponseEntity<>(medicoCriado, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MedicoDTO>> listarMedicos() {
        return ResponseEntity.ok(medicoService.listarTodos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMedico(@PathVariable Integer id) {
        medicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
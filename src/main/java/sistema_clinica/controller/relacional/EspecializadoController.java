package sistema_clinica.controller.relacional;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema_clinica.dto.EspecialidadeRequestDTO;
import sistema_clinica.dto.EspecialidadeResponseDTO;
import sistema_clinica.dto.EspecializadoRequestDTO;
import sistema_clinica.dto.EspecializadoResponseDTO;
import sistema_clinica.model.relacional.Especialidade;
import sistema_clinica.model.relacional.Especializado;
import sistema_clinica.service.relacional.EspecialidadeService;
import sistema_clinica.service.relacional.EspecializadoService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/rel/especializados")
public class EspecializadoController {
    private final EspecializadoService especializadoService;
    public EspecializadoController(EspecializadoService especializadoService) {
        this.especializadoService = especializadoService;
    }
    @GetMapping
    public ResponseEntity<List<EspecializadoResponseDTO>> listarTodos() {
        List<Especializado> relacionamentos = especializadoService.listarTodos();
        List<EspecializadoResponseDTO> dtos = relacionamentos.stream()
                .map(EspecializadoResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/medico/{medicoId}")
    public ResponseEntity<List<EspecializadoResponseDTO>> listarPorMedico(@PathVariable Integer medicoId) {
        List<Especializado> relacionamentos = especializadoService.listarPorMedico(medicoId);
        List<EspecializadoResponseDTO> dtos = relacionamentos.stream()
                .map(EspecializadoResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<EspecializadoResponseDTO> criarRelacionamento(@Valid @RequestBody EspecializadoRequestDTO dto) {
        Especializado relacionamentoSalvo = especializadoService.criarEspecializado(dto);
        EspecializadoResponseDTO responseDTO = new EspecializadoResponseDTO(relacionamentoSalvo);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/medico/{medicoId}/especialidade/{especialidadeId}")
    public ResponseEntity<Void> deletarRelacionamento(@PathVariable Integer medicoId, @PathVariable Integer especialidadeId) {
        especializadoService.deletar(medicoId, especialidadeId);
        return ResponseEntity.noContent().build();
    }
}


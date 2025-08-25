package sistema_clinica.controller.relacional;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema_clinica.dto.EspecialidadeRequestDTO;
import sistema_clinica.dto.EspecialidadeResponseDTO;
import sistema_clinica.model.relacional.Especialidade;
import sistema_clinica.service.relacional.EspecialidadeService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/rel/especialidades")
public class EspecialidadeController {
    private final EspecialidadeService especialidadeService;

    public EspecialidadeController(EspecialidadeService especialidadeService) {
        this.especialidadeService = especialidadeService;
    }

    @GetMapping
    public ResponseEntity<List<EspecialidadeResponseDTO>> listarEspecialidades() {
        List<Especialidade> especialidades = especialidadeService.listarTodas();
        List<EspecialidadeResponseDTO> responseDTOs = especialidades.stream()
                .map(EspecialidadeResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadeResponseDTO> buscarPorId(@PathVariable Integer id) {
        Especialidade especialidade = especialidadeService.buscarPorId(id);
        EspecialidadeResponseDTO responseDTO = new EspecialidadeResponseDTO(especialidade);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping
    public ResponseEntity<EspecialidadeResponseDTO> criarEspecialidade(@Valid @RequestBody EspecialidadeRequestDTO dto) {
        Especialidade especialidadeSalva = especialidadeService.criarEspecialidade(dto);
        EspecialidadeResponseDTO responseDTO = new EspecialidadeResponseDTO(especialidadeSalva);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EspecialidadeResponseDTO> atualizarEspecialidade(@PathVariable Integer id, @Valid @RequestBody EspecialidadeRequestDTO dto) {
        Especialidade especialidadeAtualizada = especialidadeService.atualizarEspecialidade(id, dto);
        EspecialidadeResponseDTO responseDTO = new EspecialidadeResponseDTO(especialidadeAtualizada);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEspecialidade(@PathVariable Integer id) {
        especialidadeService.deletarEspecialidade(id);
        return ResponseEntity.noContent().build();
    }
}


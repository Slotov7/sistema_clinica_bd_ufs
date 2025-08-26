package sistema_clinica.controller.mongo;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema_clinica.dto.EspecialidadeRequestDTO;
import sistema_clinica.dto.EspecialidadeResponseDTO;
import sistema_clinica.model.mongo.EspecialidadeDocument;
import sistema_clinica.service.mongo.EspecialidadeMongoService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/nosql/especialidades")
public class EspecialidadeMongoController {
    private final EspecialidadeMongoService especialidadeMongoService;

    public EspecialidadeMongoController(EspecialidadeMongoService especialidadeMongoService) {
        this.especialidadeMongoService = especialidadeMongoService;
    }

    @GetMapping
    public ResponseEntity<List<EspecialidadeResponseDTO>> listarEspecialidades() {
        List<EspecialidadeDocument> especialidades = especialidadeMongoService.listarTodas();
        List<EspecialidadeResponseDTO> responseDTOs = especialidades.stream()
                .map(EspecialidadeResponseDTO::new) // Usando o construtor que criamos!
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadeResponseDTO> buscarEspecialidadePorId(@PathVariable String id) {
        EspecialidadeDocument especialidade = especialidadeMongoService.buscarPorId(id);
        EspecialidadeResponseDTO responseDTO = new EspecialidadeResponseDTO(especialidade);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping
    public ResponseEntity<EspecialidadeResponseDTO> criarEspecialidade(@Valid @RequestBody EspecialidadeRequestDTO dto) {
        EspecialidadeDocument especialidadeSalva = especialidadeMongoService.criarEspecialidade(dto);
        EspecialidadeResponseDTO responseDTO = new EspecialidadeResponseDTO(especialidadeSalva);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EspecialidadeResponseDTO> atualizarEspecialidade(@PathVariable String id, @Valid @RequestBody EspecialidadeRequestDTO dto) {
        EspecialidadeDocument especialidadeAtualizada = especialidadeMongoService.atualizarEspecialidade(id, dto);
        EspecialidadeResponseDTO responseDTO = new EspecialidadeResponseDTO(especialidadeAtualizada);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEspecialidade(@PathVariable String id) {
        especialidadeMongoService.deletarEspecialidade(id);
        return ResponseEntity.noContent().build();
    }



}

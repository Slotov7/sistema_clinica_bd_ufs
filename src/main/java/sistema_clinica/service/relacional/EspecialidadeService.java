package sistema_clinica.service.relacional;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import sistema_clinica.dto.EspecialidadeRequestDTO;
import sistema_clinica.dto.EspecialidadeResponseDTO;
import sistema_clinica.model.relacional.Especialidade;
import sistema_clinica.repository.relacional.EspecialidadeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EspecialidadeService {
    private final EspecialidadeRepository especialidadeRepository;

    public EspecialidadeService(EspecialidadeRepository especialidadeRepository) {
        this.especialidadeRepository = especialidadeRepository;
    }

    public List<Especialidade> listarTodas() {
        return especialidadeRepository.findAll();
    }

    public Especialidade buscarPorId(Integer id) {
        return especialidadeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Especialidade com id: " + id + " não encontrado."));

    }

    public Especialidade criarEspecialidade(EspecialidadeRequestDTO dto) {
        Especialidade novaEspecialidade = new Especialidade();
        novaEspecialidade.setNome(dto.getNome());
        novaEspecialidade.setDescricao(dto.getDescricao());
        novaEspecialidade.setCodCbo(dto.getCodCbo());

        return especialidadeRepository.save(novaEspecialidade);
    }

    public Especialidade atualizarEspecialidade(Integer id, EspecialidadeRequestDTO dto) {
        Especialidade especialidadeExistente = buscarPorId(id);

        especialidadeExistente.setNome(dto.getNome());
        especialidadeExistente.setDescricao(dto.getDescricao());
        especialidadeExistente.setCodCbo(dto.getCodCbo());

        return especialidadeRepository.save(especialidadeExistente);
    }

    public void deletarEspecialidade(Integer id) {
        if (!especialidadeRepository.existsById(id)) {
            throw new EntityNotFoundException("Especialidade com id: " + id + "não encontrada.");
        }
        especialidadeRepository.deleteById(id);
    }

}

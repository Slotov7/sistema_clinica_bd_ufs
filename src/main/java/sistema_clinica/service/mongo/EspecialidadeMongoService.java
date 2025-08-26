package sistema_clinica.service.mongo;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import sistema_clinica.dto.EspecialidadeRequestDTO;
import sistema_clinica.model.mongo.EspecialidadeDocument;
import sistema_clinica.repository.mongo.EspecialidadeMongoRepository;


import java.util.List;

@Service
public class EspecialidadeMongoService {

    private final EspecialidadeMongoRepository especialidadeMongoRepository;

    public EspecialidadeMongoService(EspecialidadeMongoRepository especialidadeMongoRepository) {
        this.especialidadeMongoRepository = especialidadeMongoRepository;
    }

    public List<EspecialidadeDocument> listarTodas() {
        return especialidadeMongoRepository.findAll();
    }

    public EspecialidadeDocument buscarPorId(String id) {
        return especialidadeMongoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Especialidade com id: " + id + " não encontrada."));
    }

    public EspecialidadeDocument criarEspecialidade(EspecialidadeRequestDTO dto) {
        if (especialidadeMongoRepository.findByNome(dto.getNome()).isPresent()) {
            throw new IllegalArgumentException("Uma especialidade com o nome '" + dto.getNome() + "' já existe.");
        }

        EspecialidadeDocument novaEspecialidade = new EspecialidadeDocument();
        novaEspecialidade.setNome(dto.getNome());
        novaEspecialidade.setDescricao(dto.getDescricao());
        novaEspecialidade.setCodCbo(dto.getCodCbo());

        return especialidadeMongoRepository.save(novaEspecialidade);
    }

    public EspecialidadeDocument atualizarEspecialidade(String id, EspecialidadeRequestDTO dto) {
        EspecialidadeDocument especialidadeExistente = buscarPorId(id);

        especialidadeMongoRepository.findByNome(dto.getNome()).ifPresent(outraEspecialidade -> {
            if (!outraEspecialidade.getId().equals(id)) {
                throw new IllegalArgumentException("O nome '" + dto.getNome() + "' já está em uso por outra especialidade.");
            }
        });

        especialidadeExistente.setNome(dto.getNome());
        especialidadeExistente.setDescricao(dto.getDescricao());
        especialidadeExistente.setCodCbo(dto.getCodCbo());

        return especialidadeMongoRepository.save(especialidadeExistente);
    }

    public void deletarEspecialidade(String id) {
        if (!especialidadeMongoRepository.existsById(id)) {
            throw new EntityNotFoundException("Especialidade com id: " + id + " não encontrada para exclusão.");
        }
        especialidadeMongoRepository.deleteById(id);
    }
}

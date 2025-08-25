package sistema_clinica.service.relacional;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import sistema_clinica.dto.EspecializadoRequestDTO;
import sistema_clinica.model.relacional.Especialidade;
import sistema_clinica.model.relacional.Especializado;
import sistema_clinica.model.relacional.EspecializadoId;
import sistema_clinica.model.relacional.Medico;
import sistema_clinica.repository.relacional.EspecialidadeRepository;
import sistema_clinica.repository.relacional.EspecializadoRepository;
import sistema_clinica.repository.relacional.MedicoRepository;

import java.util.List;

@Service
public class EspecializadoService {
    private final EspecializadoRepository especializadoRepository;
    private final MedicoRepository medicoRepository;
    private final EspecialidadeRepository especialidadeRepository;

    public EspecializadoService(EspecializadoRepository especializadoRepository,
                                MedicoRepository medicoRepository,
                                EspecialidadeRepository especialidadeRepository) {
        this.especializadoRepository = especializadoRepository;
        this.medicoRepository = medicoRepository;
        this.especialidadeRepository = especialidadeRepository;
    }

    public List<Especializado> listarTodos() {
        return especializadoRepository.findAll();
    }

    public List<Especializado> listarPorMedico(Integer medicoId) {
        return especializadoRepository.findByIdMedicoId(medicoId);
    }

    public Especializado criarEspecializado(EspecializadoRequestDTO dto) {
        Medico medico = medicoRepository.findById(dto.getMedicoId())
                .orElseThrow(() -> new EntityNotFoundException("Médico com ID " + dto.getMedicoId() + " não encontrado."));
        Especialidade especialidade = especialidadeRepository.findById(dto.getEspecialidadeId())
                .orElseThrow(() -> new EntityNotFoundException("Especialidade com ID " + dto.getEspecialidadeId() + " não encontrada."));
        EspecializadoId id = new EspecializadoId(Math.toIntExact(medico.getId()), Math.toIntExact(especialidade.getId()));
        Especializado novoRelacionamento = new Especializado(id, medico, especialidade);
        return especializadoRepository.save(novoRelacionamento);
    }

    public void deletarEspecializado(EspecializadoRequestDTO dto) {
        EspecializadoId idParaDeletar = new EspecializadoId(dto.getMedicoId(), dto.getEspecialidadeId());

        if (!especializadoRepository.existsById(idParaDeletar)) {
            throw new EntityNotFoundException("Relacionamento não encontrado para o médico ID " + dto.getMedicoId() + " e especialidade ID " + dto.getEspecialidadeId());
        }

    }
}

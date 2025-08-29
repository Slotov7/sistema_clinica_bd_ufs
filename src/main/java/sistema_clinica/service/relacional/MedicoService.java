package sistema_clinica.service.relacional;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import sistema_clinica.dto.MedicoDTO;
import sistema_clinica.model.TipoUsuario;
import sistema_clinica.model.relacional.Especializado;
import sistema_clinica.model.relacional.Medico;
import sistema_clinica.model.relacional.Usuario;
import sistema_clinica.repository.relacional.EspecializadoRepository;
import sistema_clinica.repository.relacional.MedicoRepository;
import sistema_clinica.repository.relacional.UsuarioRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicoService {

    private final MedicoRepository medicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EspecializadoRepository especializadoRepository;

    public MedicoService(MedicoRepository medicoRepository,
                         UsuarioRepository usuarioRepository,
                         EspecializadoRepository especializadoRepository) {
        this.medicoRepository = medicoRepository;
        this.usuarioRepository = usuarioRepository;
        this.especializadoRepository = especializadoRepository;
    }

    public MedicoDTO criar(MedicoDTO dto) {
        Usuario usuario = usuarioRepository.findById(Integer.valueOf(dto.usuarioId()))
                .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + dto.usuarioId() + " não encontrado."));

        if (usuario.getTipoUsuario() != TipoUsuario.MEDICO) {
            throw new IllegalArgumentException("O usuário com ID " + dto.usuarioId() + " não é do tipo MEDICO.");
        }

        if (medicoRepository.existsById(usuario.getId())) {
            throw new IllegalArgumentException("Já existe um médico cadastrado para o usuário com ID " + dto.usuarioId());
        }

        Medico novoMedico = new Medico();
        novoMedico.setUsuario(usuario);
        novoMedico.setCrm(dto.crm());

        Medico medicoSalvo = medicoRepository.save(novoMedico);
        return new MedicoDTO(medicoSalvo);
    }

    public List<MedicoDTO> listarTodos() {
        return medicoRepository.findAll().stream()
                .map(MedicoDTO::new)
                .collect(Collectors.toList());
    }

    public MedicoDTO buscarPorId(Integer id) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Médico com ID " + id + " não encontrado."));
        return new MedicoDTO(medico);
    }

    @Transactional
    public MedicoDTO atualizar(Integer id, MedicoDTO dto) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Médico com ID " + id + " não encontrado."));

        medico.setCrm(dto.crm());
        Medico medicoAtualizado = medicoRepository.save(medico);
        return new MedicoDTO(medicoAtualizado);
    }

    @Transactional
    public void deletar(Integer id) {
        if (!medicoRepository.existsById(id)) {
            throw new EntityNotFoundException("Médico com ID " + id + " não encontrado.");
        }

        List<Especializado> relacionamentos = especializadoRepository.findByIdMedicoId(id);
        if (!relacionamentos.isEmpty()) {
            especializadoRepository.deleteAll(relacionamentos);
        }

        medicoRepository.deleteById(id);
    }
}
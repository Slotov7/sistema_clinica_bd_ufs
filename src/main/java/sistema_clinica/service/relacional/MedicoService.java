package sistema_clinica.service.relacional;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import sistema_clinica.dto.MedicoDTO;
import sistema_clinica.model.TipoUsuario;
import sistema_clinica.model.relacional.Medico;
import sistema_clinica.model.relacional.Usuario;
import sistema_clinica.repository.relacional.MedicoRepository;
import sistema_clinica.repository.relacional.UsuarioRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicoService {

    private final MedicoRepository medicoRepository;
    private final UsuarioRepository usuarioRepository;

    public MedicoService(MedicoRepository medicoRepository, UsuarioRepository usuarioRepository) {
        this.medicoRepository = medicoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public MedicoDTO criar(MedicoDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
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

    public void deletar(Integer id) {
        if (!medicoRepository.existsById(id)) {
            throw new EntityNotFoundException("Médico com ID " + id + " não encontrado.");
        }
        medicoRepository.deleteById(id);
    }
}
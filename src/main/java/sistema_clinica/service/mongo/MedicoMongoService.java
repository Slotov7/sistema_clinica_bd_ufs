
package sistema_clinica.service.mongo;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import sistema_clinica.dto.MedicoDTO;
import sistema_clinica.model.TipoUsuario;
import sistema_clinica.model.mongo.EspecialidadeDocument;
import sistema_clinica.model.mongo.UsuarioDocument;
import sistema_clinica.repository.mongo.UsuarioMongoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicoMongoService {

    private final UsuarioMongoRepository usuarioRepository;
    private final EspecialidadeMongoService especialidadeMongoService;

    public MedicoMongoService(UsuarioMongoRepository usuarioRepository, EspecialidadeMongoService especialidadeMongoService) {
        this.usuarioRepository = usuarioRepository;
        this.especialidadeMongoService = especialidadeMongoService;
    }

    public MedicoDTO criar(MedicoDTO dto) {
        UsuarioDocument usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + dto.usuarioId() + " não encontrado."));

        if (usuario.getTipoUsuario() != TipoUsuario.MEDICO) {
            throw new IllegalArgumentException("O usuário com ID " + dto.usuarioId() + " não é do tipo MEDICO.");
        }

        if (usuario.getCrm() != null) {
             throw new IllegalArgumentException("Já existe um médico cadastrado para o usuário com ID " + dto.usuarioId());
        }

        usuario.setCrm(dto.crm());
        UsuarioDocument medicoSalvo = usuarioRepository.save(usuario);
        return new MedicoDTO(medicoSalvo);
    }

    public List<MedicoDTO> listarTodos() {
        return usuarioRepository.findByTipoUsuario(TipoUsuario.MEDICO).stream()
                .map(MedicoDTO::new)
                .collect(Collectors.toList());
    }

    public MedicoDTO buscarPorId(String id) {
        UsuarioDocument medico = usuarioRepository.findByIdAndTipoUsuario(id, TipoUsuario.MEDICO)
                .orElseThrow(() -> new EntityNotFoundException("Médico com ID " + id + " não encontrado."));
        return new MedicoDTO(medico);
    }

    public UsuarioDocument adicionarEspecialidade(String medicoId, String especialidadeId) {
        UsuarioDocument medico = usuarioRepository.findByIdAndTipoUsuario(medicoId, TipoUsuario.MEDICO)
                .orElseThrow(() -> new EntityNotFoundException("Médico com ID " + medicoId + " não encontrado."));

        EspecialidadeDocument especialidade = especialidadeMongoService.buscarPorId(especialidadeId);

        if (medico.getEspecialidadeIds().contains(especialidade.getId())) {
            throw new IllegalArgumentException("O médico já possui esta especialidade.");
        }
        medico.getEspecialidadeIds().add(especialidade.getId());
        return usuarioRepository.save(medico);
    }

    public UsuarioDocument removerEspecialidade(String medicoId, String especialidadeId) {
        UsuarioDocument medico = usuarioRepository.findByIdAndTipoUsuario(medicoId, TipoUsuario.MEDICO)
                .orElseThrow(() -> new EntityNotFoundException("Médico com ID " + medicoId + " não encontrado."));

        boolean removeu = medico.getEspecialidadeIds().remove(especialidadeId);
        if (!removeu) {
            throw new EntityNotFoundException("O médico não possui a especialidade com id: " + especialidadeId + " para ser removida.");
        }
        return usuarioRepository.save(medico);
    }

    public MedicoDTO atualizar(String id, MedicoDTO dto) {
        UsuarioDocument medico = usuarioRepository.findByIdAndTipoUsuario(id, TipoUsuario.MEDICO)
                .orElseThrow(() -> new EntityNotFoundException("Médico com ID " + id + " não encontrado."));

        medico.setCrm(dto.crm());
        UsuarioDocument medicoAtualizado = usuarioRepository.save(medico);
        return new MedicoDTO(medicoAtualizado);
    }

    public void deletar(String id) {
        UsuarioDocument medico = usuarioRepository.findByIdAndTipoUsuario(id, TipoUsuario.MEDICO)
                .orElseThrow(() -> new EntityNotFoundException("Médico com ID " + id + " não encontrado."));

        // Em vez de deletar o usuário, apenas removemos o status de médico
        medico.setCrm(null);
        // Limpa também as especialidades associadas
        medico.getEspecialidadeIds().clear();
        usuarioRepository.save(medico);
    }
}

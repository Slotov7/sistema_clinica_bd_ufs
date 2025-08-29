
package sistema_clinica.service.mongo;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import sistema_clinica.dto.MedicoDTO;
import sistema_clinica.model.TipoUsuario;
import sistema_clinica.model.mongo.UsuarioDocument;
import sistema_clinica.repository.mongo.UsuarioMongoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicoMongoService {

    private final UsuarioMongoRepository usuarioRepository;

    public MedicoMongoService(UsuarioMongoRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public MedicoDTO criar(MedicoDTO dto) {
        UsuarioDocument usuario = usuarioRepository.findById(String.valueOf(dto.usuarioId()))
                .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + dto.usuarioId() + " não encontrado."));

        if (usuario.getTipoUsuario() != TipoUsuario.MEDICO) {
            throw new IllegalArgumentException("O usuário com ID " + dto.usuarioId() + " não é do tipo MEDICO.");
        }

        if (usuario.getCrm() != null) {
             throw new IllegalArgumentException("Já existe um médico cadastrado para o usuário com ID " + dto.usuarioId());
        }

        usuario.setCrm(String.valueOf(dto.crm()));
        UsuarioDocument medicoSalvo = usuarioRepository.save(usuario);
        return new MedicoDTO(medicoSalvo.getId(), medicoSalvo.getNome(), Integer.parseInt(medicoSalvo.getCrm()));
    }

    public List<MedicoDTO> listarTodos() {
        return usuarioRepository.findByTipoUsuario(TipoUsuario.MEDICO).stream()
                .map(medico -> new MedicoDTO(medico.getId(), medico.getNome(), Integer.parseInt(medico.getCrm())))
                .collect(Collectors.toList());
    }

    public MedicoDTO buscarPorId(String id) {
        UsuarioDocument medico = usuarioRepository.findByIdAndTipoUsuario(id, TipoUsuario.MEDICO)
                .orElseThrow(() -> new EntityNotFoundException("Médico com ID " + id + " não encontrado."));
        return new MedicoDTO(medico.getId(), medico.getNome(), Integer.parseInt(medico.getCrm()));
    }

    public MedicoDTO atualizar(String id, MedicoDTO dto) {
        UsuarioDocument medico = usuarioRepository.findByIdAndTipoUsuario(id, TipoUsuario.MEDICO)
                .orElseThrow(() -> new EntityNotFoundException("Médico com ID " + id + " não encontrado."));

        medico.setCrm(String.valueOf(dto.crm()));
        UsuarioDocument medicoAtualizado = usuarioRepository.save(medico);
        return new MedicoDTO(medicoAtualizado.getId(), medicoAtualizado.getNome(), Integer.parseInt(medicoAtualizado.getCrm()));
    }

    public void deletar(String id) {
        UsuarioDocument medico = usuarioRepository.findByIdAndTipoUsuario(id, TipoUsuario.MEDICO)
                .orElseThrow(() -> new EntityNotFoundException("Médico com ID " + id + " não encontrado."));

        // Em vez de deletar o usuário, apenas removemos o status de médico
        medico.setCrm(null);
        usuarioRepository.save(medico);
    }
}

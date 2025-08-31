package sistema_clinica.dto;

import jakarta.validation.constraints.NotNull;
import sistema_clinica.model.mongo.UsuarioDocument;
import sistema_clinica.model.relacional.Medico;

public record MedicoDTO(
        @NotNull(message = "O id é obrigatório")
        String usuarioId,

        String nomeUsuario,
        @NotNull(message = "O CRM é obrigatório.")
        String crm
) {
    public MedicoDTO(Medico medico) {
        this(
                String.valueOf(medico.getId()),
                medico.getUsuario().getNome(),
                medico.getCrm()
        );
    }

    public MedicoDTO(UsuarioDocument usuario) {
        this(
                usuario.getId(),
                usuario.getNome(),
                usuario.getCrm()
        );
    }
}
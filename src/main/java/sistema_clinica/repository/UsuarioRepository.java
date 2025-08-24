package sistema_clinica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sistema_clinica.model.relacional.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {
    Optional<Usuario> findByUsername(String username);
}

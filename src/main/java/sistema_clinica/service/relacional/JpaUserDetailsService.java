package sistema_clinica.service.relacional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sistema_clinica.repository.relacional.UsuarioRepository;
import sistema_clinica.security.SecurityUsuario;

@Service
public class JpaUserDetailsService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    public JpaUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByUsername(username)
                .map(SecurityUsuario::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
    }
}

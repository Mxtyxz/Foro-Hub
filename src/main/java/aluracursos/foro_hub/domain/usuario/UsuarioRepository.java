package aluracursos.foro_hub.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Boolean existsByNombreEqualsIgnoreCase(String nombre);

    Boolean existsByEmailEqualsIgnoreCase(String email);

    Usuario findByEmailEquals(String email);

    UserDetails findByNombre(String username);
}

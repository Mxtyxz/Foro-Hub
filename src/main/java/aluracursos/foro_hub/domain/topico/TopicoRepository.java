package aluracursos.foro_hub.domain.topico;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    boolean existsByTituloEqualsIgnoreCaseAndMensajeEqualsIgnoreCase(String titulo, String mensaje);
}

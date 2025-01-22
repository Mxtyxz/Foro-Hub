package aluracursos.foro_hub.domain.curso;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    boolean existsByNombreEqualsIgnoreCase(String nombre);

    Curso getReferenceByNombre(String nombre);
}

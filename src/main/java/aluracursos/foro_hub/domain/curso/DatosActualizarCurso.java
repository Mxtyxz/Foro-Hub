package aluracursos.foro_hub.domain.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarCurso(
        @NotNull
        Long idCurso,
        @NotBlank
        String nombre,
        @NotBlank
        String categoria
) {
}

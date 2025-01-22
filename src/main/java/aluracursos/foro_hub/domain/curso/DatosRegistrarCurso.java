package aluracursos.foro_hub.domain.curso;

import jakarta.validation.constraints.NotBlank;

public record DatosRegistrarCurso(
        @NotBlank
        String nombre,
        @NotBlank
        String categoria) {}

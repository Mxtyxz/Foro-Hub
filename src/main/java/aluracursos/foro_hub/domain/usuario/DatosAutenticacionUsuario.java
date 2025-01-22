package aluracursos.foro_hub.domain.usuario;

import jakarta.validation.constraints.NotBlank;

public record DatosAutenticacionUsuario(
        @NotBlank
        String usuario,
        @NotBlank
        String clave
) {
}

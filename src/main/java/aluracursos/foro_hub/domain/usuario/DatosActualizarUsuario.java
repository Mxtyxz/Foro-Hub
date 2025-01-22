package aluracursos.foro_hub.domain.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarUsuario(
        @NotNull
        Long idUsuario,
        @NotBlank
        String nombre,
        @NotBlank
        String email,
        @NotBlank
        String clave,
        @NotNull
        Long perfil
) {
}

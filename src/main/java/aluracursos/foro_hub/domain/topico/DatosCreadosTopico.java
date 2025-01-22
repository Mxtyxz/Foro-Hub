package aluracursos.foro_hub.domain.topico;

public record DatosCreadosTopico(
        Long id,
        String titulo,
        String mensaje,
        String fecha,
        String status,
        String usuarioTopico,
        String curso
) {
}

package aluracursos.foro_hub.domain.respuesta;

import aluracursos.foro_hub.domain.topico.Topico;
import aluracursos.foro_hub.domain.usuario.Usuario;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "respuestas")
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRespuesta;
    private String mensaje;
    @ManyToOne
    private Topico topico;
    @DateTimeFormat
    private LocalDateTime fecha;
    @ManyToOne
    private Usuario usuario;
    private Boolean solucion;

    public void actualizarRespuesta(String mensaje, Topico topicoSeleccionado, Usuario usuarioSeleccionado) {
        this.mensaje = mensaje;
        this.topico = topicoSeleccionado;
        this.usuario = usuarioSeleccionado;
    }

    public Respuesta() {}

    public Respuesta(Long idRespuesta, String mensaje, Topico topico, LocalDateTime fecha, Usuario usuario, Boolean solucion) {
        this.idRespuesta = idRespuesta;
        this.mensaje = mensaje;
        this.topico = topico;
        this.fecha = fecha;
        this.usuario = usuario;
        this.solucion = solucion;
    }

    public Long getIdRespuesta() {
        return idRespuesta;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Topico getTopico() {
        return topico;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Boolean getSolucion() {
        return solucion;
    }
}

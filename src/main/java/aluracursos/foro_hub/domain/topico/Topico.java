package aluracursos.foro_hub.domain.topico;

import aluracursos.foro_hub.domain.curso.Curso;
import aluracursos.foro_hub.domain.respuesta.Respuesta;
import aluracursos.foro_hub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "topicos")
@Entity
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    @DateTimeFormat
    private LocalDateTime fecha;
    private Boolean estado;
    @ManyToOne
    private Usuario usuario;
    @ManyToOne
    private Curso curso;
    @OneToMany(mappedBy = "topico", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Respuesta> respuestas;

    public void actualizarTopico(String titulo, String mensaje, Usuario usuario, Curso curso) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.usuario = usuario;
        this.curso = curso;
    }

    public Topico() {}

    public Topico(Long id, String titulo, String mensaje, LocalDateTime fecha, Boolean estado, Usuario usuario, Curso curso, List<Respuesta> respuestas) {
        this.id = id;
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fecha = fecha;
        this.estado = estado;
        this.usuario = usuario;
        this.curso = curso;
        this.respuestas = respuestas;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public Boolean getEstado() {
        return estado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Curso getCurso() {
        return curso;
    }

    public List<Respuesta> getRespuestas() {
        return respuestas;
    }
}

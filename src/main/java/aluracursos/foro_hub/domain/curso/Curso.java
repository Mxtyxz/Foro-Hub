package aluracursos.foro_hub.domain.curso;

import aluracursos.foro_hub.domain.topico.Topico;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "cursos")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    @OneToMany(mappedBy = "curso", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Topico> topicos;

    public void actualizarTopico(String nombre, String categoria) {
        this.nombre = nombre;
        this.categoria = Categoria.valueOf(categoria);
    }

    public Curso() {}

    public Curso(Long id, String nombre, Categoria categoria, List<Topico> topicos) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.topicos = topicos;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Categoria getCategoria() {
        return categoria;
    }
}

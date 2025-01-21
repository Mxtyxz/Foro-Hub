package aluracursos.foro_hub.domain.curso;

public class Curso {
    private Long id;
    private String nombre;
    private Categoria categoria;

    public Curso() {}

    public Curso(Long id, String nombre, Categoria categoria) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
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

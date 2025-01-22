package aluracursos.foro_hub.domain.perfil;

import aluracursos.foro_hub.domain.usuario.Usuario;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "perfiles")
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombrePerfil;
    @OneToMany(mappedBy = "perfil")
    private List<Usuario> usuarios;

    public Perfil() {}

    public Perfil(String nombrePerfil, Long id) {
        this.nombrePerfil = nombrePerfil;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNombrePerfil() {
        return nombrePerfil;
    }
}


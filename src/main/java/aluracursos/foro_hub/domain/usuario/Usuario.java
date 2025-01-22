package aluracursos.foro_hub.domain.usuario;

import aluracursos.foro_hub.domain.perfil.Perfil;
import aluracursos.foro_hub.domain.respuesta.Respuesta;
import aluracursos.foro_hub.domain.topico.Topico;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String password;
    @ManyToOne
    private Perfil perfil;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Topico> topicos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Respuesta> respuestas;

    public void actualizarRespuesta(String nombre, String email, String clave, Perfil perfil){
        this.nombre = nombre;
        this.email = email;
        this.password = clave;
        this.perfil = perfil;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return nombre;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Usuario() {}

    public Usuario(Long id, String nombre, String email, String password, Perfil perfil, List<Topico> topicos, List<Respuesta> respuestas) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.perfil = perfil;
        this.topicos = topicos;
        this.respuestas = respuestas;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Topico> getTopicos() {
        return topicos;
    }

    public List<Respuesta> getRespuestas() {
        return respuestas;
    }
}

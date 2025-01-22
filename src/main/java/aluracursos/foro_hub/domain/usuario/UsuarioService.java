package aluracursos.foro_hub.domain.usuario;

import aluracursos.foro_hub.domain.perfil.Perfil;
import aluracursos.foro_hub.domain.perfil.PerfilRepository;
import aluracursos.foro_hub.domain.validaciones.usuario.ValidadorDeUsuario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    public final UsuarioRepository repositorio;
    public final List<ValidadorDeUsuario> validadores;
    public final PerfilRepository repositorioPerfil;

    public UsuarioService(UsuarioRepository repositorio, List<ValidadorDeUsuario> validadores, PerfilRepository repositorioPerfil){
        this.repositorio = repositorio;
        this. validadores = validadores;
        this.repositorioPerfil = repositorioPerfil;
    }

    public DatosCreadosUsuario registrarUsuario(DatosRegistrarUsuario datos) {
        validadores.forEach(v -> v.validar(datos));

        Perfil perfilEnviado = repositorioPerfil.getReferenceById(datos.perfil());

        Usuario nuevoUsuario = new Usuario(null, datos.nombre(), datos.email(), datos.clave(), perfilEnviado, null, null);
        repositorio.save(nuevoUsuario);

        Usuario usuarioCreado = repositorio.findByEmailEquals(nuevoUsuario.getEmail());

        return new DatosCreadosUsuario(usuarioCreado.getId(), usuarioCreado.getNombre(), usuarioCreado.getEmail(), usuarioCreado.getPerfil().getNombrePerfil());
    }


    public DatosCreadosUsuario actualizarTopico(DatosActualizarUsuario datos) {
        DatosRegistrarUsuario datosParaVerificadores = new DatosRegistrarUsuario(datos.nombre(), datos.email(), datos.clave(), datos.perfil());

        validadores.forEach(v -> v.validar(datosParaVerificadores));

        Usuario usuarioSeleccionado = repositorio.getReferenceById(datos.idUsuario());
        usuarioSeleccionado.actualizarRespuesta(datos.nombre(), datos.email(), datos.clave(), repositorioPerfil.getReferenceById(datos.perfil()));

        repositorio.save(usuarioSeleccionado);

        return new DatosCreadosUsuario(usuarioSeleccionado.getId(), usuarioSeleccionado.getNombre(), usuarioSeleccionado.getEmail(),
                usuarioSeleccionado.getPerfil().getNombrePerfil());
    }
}

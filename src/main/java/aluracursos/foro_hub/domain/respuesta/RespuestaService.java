package aluracursos.foro_hub.domain.respuesta;

import aluracursos.foro_hub.domain.topico.Topico;
import aluracursos.foro_hub.domain.topico.TopicoRepository;
import aluracursos.foro_hub.domain.usuario.Usuario;
import aluracursos.foro_hub.domain.usuario.UsuarioRepository;
import aluracursos.foro_hub.domain.validaciones.respuesta.ValidadorDeRespuesta;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RespuestaService {

    private final RespuestaRepository repositorio;
    private final UsuarioRepository repositorioUsuario;
    private final TopicoRepository repositorioTopico;
    private final List<ValidadorDeRespuesta> validadores;

    public RespuestaService(RespuestaRepository repositorio, UsuarioRepository repositorioUsuario,
                            TopicoRepository repositorioTopico, List<ValidadorDeRespuesta> validadores) {
        this.repositorio = repositorio;
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioTopico = repositorioTopico;
        this.validadores = validadores;
    }


    public DatosCreadosRespuesta registrarRespuesta(@Valid DatosRegistrarRespuesta datos) {
        validadores.forEach(v -> v.validar(datos));

        Topico topicoSeleccionado = repositorioTopico.getReferenceById(datos.idTopico());
        Usuario usuarioSeleccionado = repositorioUsuario.getReferenceById(datos.idUsuario());

        Respuesta nuevaRespuesta = new Respuesta(null, datos.mensaje(), topicoSeleccionado, LocalDateTime.now(), usuarioSeleccionado, false);
        repositorio.save(nuevaRespuesta);

        return new DatosCreadosRespuesta(nuevaRespuesta.getIdRespuesta(), nuevaRespuesta.getMensaje(), nuevaRespuesta.getTopico().getTitulo(),
                nuevaRespuesta.getFecha().toString(), nuevaRespuesta.getUsuario().getNombre());
    }

    public DatosCreadosRespuesta actualizarTopico(DatosActualizarRespuesta datos) {

        DatosRegistrarRespuesta datosParaVerificadores = new DatosRegistrarRespuesta(datos.mensaje(), datos.idTopico(), datos.idUsuario());

        validadores.forEach(v -> v.validar(datosParaVerificadores));

        Topico topicoSeleccionado = repositorioTopico.getReferenceById(datos.idTopico());
        Usuario usuarioSeleccionado = repositorioUsuario.getReferenceById(datos.idUsuario());
        Respuesta respuestaSeleccionada = repositorio.getReferenceById(datos.idRespuesta());

        respuestaSeleccionada.actualizarRespuesta(datos.mensaje(), topicoSeleccionado, usuarioSeleccionado);

        repositorio.save(respuestaSeleccionada);

        return new DatosCreadosRespuesta(respuestaSeleccionada.getIdRespuesta(), respuestaSeleccionada.getMensaje(), respuestaSeleccionada.getTopico().getTitulo(),
                respuestaSeleccionada.getFecha().toString(), respuestaSeleccionada.getUsuario().getNombre());
    }
}

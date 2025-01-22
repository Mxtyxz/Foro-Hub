package aluracursos.foro_hub.controller;

import aluracursos.foro_hub.domain.ValidacionException;
import aluracursos.foro_hub.domain.respuesta.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/respuestas")
public class RespuestaController {

    private final RespuestaRepository repositorio;
    private final RespuestaService service;

    public RespuestaController(RespuestaRepository repositorio, RespuestaService service) {
        this.repositorio = repositorio;
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DatosCreadosRespuesta> registrarRespuesta(@RequestBody @Valid DatosRegistrarRespuesta datos, UriComponentsBuilder uriComponentsBuilder){
        DatosCreadosRespuesta detalleRespuesta = service.registrarRespuesta(datos);
        URI url = uriComponentsBuilder.path("/respuestas/{id}").buildAndExpand(detalleRespuesta.id()).toUri();

        return ResponseEntity.created(url).body(detalleRespuesta);
    }

    @GetMapping
    public ResponseEntity<List<DatosCreadosRespuesta>> listarRespuestas(){
        List<DatosCreadosRespuesta> listaRespuestasCreadas = repositorio.findAll().stream()
                .map(r -> new DatosCreadosRespuesta(r.getIdRespuesta(), r.getMensaje(), r.getTopico().getTitulo(), r.getFecha().toString(),
                        r.getUsuario().getNombre()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listaRespuestasCreadas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosCreadosRespuesta> listarRespuestaEspecifica(@PathVariable Long id){
        if (!repositorio.existsById(id)){
            throw new ValidacionException("La respuesta enviada para listar no existe.");
        }
        Respuesta topicoEncontrado = repositorio.getReferenceById(id);

        DatosCreadosRespuesta respuestaSeleccionada = new DatosCreadosRespuesta(topicoEncontrado.getIdRespuesta(), topicoEncontrado.getMensaje(),
                topicoEncontrado.getTopico().getTitulo(), topicoEncontrado.getFecha().toString(),
                topicoEncontrado.getUsuario().getNombre());

        return ResponseEntity.ok(respuestaSeleccionada);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosCreadosRespuesta> actualizarRespuesta(@PathVariable Long id, @RequestBody @Valid DatosRegistrarRespuesta datos, UriComponentsBuilder uriComponentsBuilder){
        if (!repositorio.existsById(id)){
            throw new ValidacionException("La respuesta enviada para actualizar no existe.");
        }
        DatosActualizarRespuesta datosParaActualizar = new DatosActualizarRespuesta(id, datos.mensaje(), datos.idTopico(), datos.idUsuario());
        DatosCreadosRespuesta respuestaActualizado = service.actualizarTopico(datosParaActualizar);
        URI url = uriComponentsBuilder.path("/respuestas/{id}").buildAndExpand(respuestaActualizado.id()).toUri();

        return ResponseEntity.ok(respuestaActualizado);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarRespuesta(@PathVariable Long id){
        if (!repositorio.existsById(id)){
            throw new ValidacionException("La respuesta enviada para eliminar no existe.");
        }
        repositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

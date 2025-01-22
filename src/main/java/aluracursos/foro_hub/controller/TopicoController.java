package aluracursos.foro_hub.controller;

import aluracursos.foro_hub.domain.ValidacionException;
import aluracursos.foro_hub.domain.topico.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private final TopicoService service;
    private final TopicoRepository repositorio;

    public TopicoController(TopicoService service, TopicoRepository repositorio) {
        this.service = service;
        this.repositorio = repositorio;
    }

    @PostMapping
    public ResponseEntity<DatosCreadosTopico> registrarTopico(@RequestBody @Valid DatosRegistrarTopico datos, UriComponentsBuilder uriComponentsBuilder){
        DatosCreadosTopico detalleTopico = service.registrarTopico(datos);
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(detalleTopico.id()).toUri();

        return ResponseEntity.created(url).body(detalleTopico);
    }

    @GetMapping
    public ResponseEntity<List<DatosCreadosTopico>> listarTopicos(){
        List<DatosCreadosTopico> listaTopicosCreados = repositorio.findAll().stream()
                .map(t -> new DatosCreadosTopico(t.getId(), t.getTitulo(), t.getMensaje(), t.getFecha().toString(),
                        t.getEstado().toString(), t.getUsuario().getNombre(), t.getCurso().getNombre()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listaTopicosCreados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosCreadosTopico> listarTopicoEspecifico(@PathVariable Long id){
        if (!repositorio.existsById(id)){
            throw new ValidacionException("El tópico enviado para buscar no existe.");
        }

        Topico topicoEncontrado = repositorio.getReferenceById(id);

        DatosCreadosTopico topicoSeleccionado = new DatosCreadosTopico(topicoEncontrado.getId(), topicoEncontrado.getTitulo(),
                topicoEncontrado.getMensaje(), topicoEncontrado.getFecha().toString(), topicoEncontrado.getEstado().toString(),
                topicoEncontrado.getUsuario().getNombre(), topicoEncontrado.getCurso().getNombre());

        return ResponseEntity.ok(topicoSeleccionado);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosCreadosTopico> actualizarTopico(@PathVariable Long id, @RequestBody @Valid DatosRegistrarTopico datos, UriComponentsBuilder uriComponentsBuilder){
        if (!repositorio.existsById(id)){
            throw new ValidacionException("El tópico enviado para actualizar no existe.");
        }
        DatosActualizarTopico datosParaActualizar = new DatosActualizarTopico(id, datos.titulo(), datos.mensaje(), datos.idUsuario(), datos.idCurso());
        DatosCreadosTopico topicoActualizado = service.actualizarTopico(datosParaActualizar);

        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topicoActualizado.id()).toUri();

        return ResponseEntity.ok(topicoActualizado);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id){
        if (!repositorio.existsById(id)){
            throw new ValidacionException("El tópico enviado para eliminar no existe.");
        }
        repositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

package aluracursos.foro_hub.controller;

import aluracursos.foro_hub.domain.ValidacionException;
import aluracursos.foro_hub.domain.curso.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    private final CursoService service;
    private final CursoRepository repositorio;

    public CursoController(CursoService service, CursoRepository repositorio){
        this.service = service;
        this.repositorio = repositorio;
    }

    @PostMapping
    public ResponseEntity<DatosCreadosCurso> registrarCurso(@RequestBody @Valid DatosRegistrarCurso datos,
                                                            UriComponentsBuilder uriComponentsBuilder){
        DatosCreadosCurso detalleCurso = service.registrarCurso(datos);

        URI url = uriComponentsBuilder.path("/cursos/{id}").buildAndExpand(detalleCurso.id()).toUri();

        return ResponseEntity.created(url).body(detalleCurso);
    }

    @GetMapping
    public ResponseEntity<List<DatosCreadosCurso>> listarCursos(){
        List<DatosCreadosCurso> listaCursosCreados = repositorio.findAll().stream()
                .map(c -> new DatosCreadosCurso(c.getId(), c.getNombre(), c.getCategoria().toString()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listaCursosCreados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosCreadosCurso> listarCursoEspecifico(@PathVariable Long id){
        if (!repositorio.existsById(id)){
            throw new ValidacionException("El curso enviado para buscar no existe.");
        }
        Curso cursoEncontrado = repositorio.getReferenceById(id);

        DatosCreadosCurso cursoSeleccionado = new DatosCreadosCurso(cursoEncontrado.getId(), cursoEncontrado.getNombre(),
                cursoEncontrado.getCategoria().toString());
        return ResponseEntity.ok(cursoSeleccionado);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosCreadosCurso> actualizarCurso(@PathVariable Long id, @RequestBody @Valid DatosRegistrarCurso datos, UriComponentsBuilder uriComponentsBuilder){
        if (!repositorio.existsById(id)){
            throw new ValidacionException("El curso enviado para actualizar no existe.");
        }
        DatosActualizarCurso datosParaActualizar = new DatosActualizarCurso(id, datos.nombre(), datos.categoria());

        DatosCreadosCurso cursoActualizado = service.actualizarCurso(datosParaActualizar);
        URI url = uriComponentsBuilder.path("/cursos/{id}").buildAndExpand(cursoActualizado.id()).toUri();
        return ResponseEntity.ok(cursoActualizado);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarCurso(@PathVariable Long id){

        if (!repositorio.existsById(id)){
            throw new ValidacionException("El curso enviado para eliminar no existe.");
        }
        repositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

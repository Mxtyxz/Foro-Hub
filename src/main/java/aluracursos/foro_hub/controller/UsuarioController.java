package aluracursos.foro_hub.controller;

import aluracursos.foro_hub.domain.ValidacionException;
import aluracursos.foro_hub.domain.usuario.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {


    private final UsuarioService service;
    private final UsuarioRepository repositorio;

    public UsuarioController(UsuarioService service, UsuarioRepository repositorio){
        this.service = service;
        this.repositorio = repositorio;
    }

    @PostMapping
    public ResponseEntity<DatosCreadosUsuario> registrarUsuario(@RequestBody @Valid DatosRegistrarUsuario datos, UriComponentsBuilder uriComponentsBuilder){
        DatosCreadosUsuario detalleUsuario = service.registrarUsuario(datos);
        URI url = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(detalleUsuario.id()).toUri();

        return ResponseEntity.created(url).body(detalleUsuario);
    }

    @GetMapping
    public ResponseEntity<List<DatosCreadosUsuario>> listarUsuarios(){
        List<DatosCreadosUsuario> listaUsuariosCreados = repositorio.findAll().stream()
                .map(u -> new DatosCreadosUsuario(u.getId(), u.getNombre(), u.getEmail(), u.getPerfil().getNombrePerfil()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listaUsuariosCreados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosCreadosUsuario> listarUsuarioEspecifico(@PathVariable Long id){
        if (!repositorio.existsById(id)){
            throw new ValidacionException("El usuario enviado para buscar no existe.");
        }

        Usuario usuarioEncontrado = repositorio.getReferenceById(id);

        DatosCreadosUsuario usuarioSeleccionado = new DatosCreadosUsuario(usuarioEncontrado.getId(), usuarioEncontrado.getNombre(),
                usuarioEncontrado.getEmail(), usuarioEncontrado.getPerfil().getNombrePerfil());

        return ResponseEntity.ok(usuarioSeleccionado);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosCreadosUsuario> actualizarUsuario(@PathVariable Long id, @RequestBody @Valid DatosRegistrarUsuario datos, UriComponentsBuilder uriComponentsBuilder){
        if (!repositorio.existsById(id)){
            throw new ValidacionException("El Usuario enviado para actualizar no existe.");
        }

        if (repositorio.getReferenceById(id).getId() == 1){
            throw new ValidacionException("No se puede actualizar el usuario administrador.");
        }
        DatosActualizarUsuario datosParaActualizar = new DatosActualizarUsuario(id, datos.nombre(), datos.email(), datos.clave(), datos.perfil());
        DatosCreadosUsuario usuarioActualizado = service.actualizarTopico(datosParaActualizar);
        URI url = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuarioActualizado.id()).toUri();

        return ResponseEntity.ok(usuarioActualizado);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarUsuario(@PathVariable Long id){
        if (!repositorio.existsById(id)){
            throw new ValidacionException("El usuario enviado para eliminar no existe.");
        }

        if (repositorio.getReferenceById(id).getId() == 1){
            throw new ValidacionException("No se puede eliminar el usuario administrador.");
        }
        repositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

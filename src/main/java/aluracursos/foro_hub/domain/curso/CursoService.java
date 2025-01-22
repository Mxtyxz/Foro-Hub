package aluracursos.foro_hub.domain.curso;

import aluracursos.foro_hub.domain.validaciones.curso.ValidadorDeCurso;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {

    public final CursoRepository repositorio;
    public final List<ValidadorDeCurso> validadores;

    public CursoService(CursoRepository repositorio, List<ValidadorDeCurso> validadores) {
        this.repositorio = repositorio;
        this.validadores = validadores;
    }


    public DatosCreadosCurso registrarCurso(@Valid DatosRegistrarCurso datos) {
        validadores.forEach(v -> v.validar(datos));

        Curso cursoNuevo = new Curso(null, datos.nombre(), Categoria.valueOf(datos.categoria()), null);
        repositorio.save(cursoNuevo);

        Curso cursoCreado = repositorio.getReferenceByNombre(cursoNuevo.getNombre());
        return new DatosCreadosCurso(cursoCreado.getId(), cursoCreado.getNombre(), cursoCreado.getCategoria().toString());
    }

    public DatosCreadosCurso actualizarCurso(DatosActualizarCurso datos) {
        DatosRegistrarCurso datosParaVerificadores = new DatosRegistrarCurso(datos.nombre(), datos.categoria());

        validadores.forEach(v -> v.validar(datosParaVerificadores));

        Curso cursoSeleccionado = repositorio.getReferenceById(datos.idCurso());
        cursoSeleccionado.actualizarTopico(datos.nombre(), datos.categoria());
        repositorio.save(cursoSeleccionado);

        return new DatosCreadosCurso(cursoSeleccionado.getId(), cursoSeleccionado.getNombre(), cursoSeleccionado.getCategoria().toString());
    }
}

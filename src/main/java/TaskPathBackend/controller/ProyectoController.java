package TaskPathBackend.controller;

import TaskPathBackend.dto.ProyectoDTO;
import TaskPathBackend.service.ProyectoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proyectos")
@RequiredArgsConstructor
public class ProyectoController {

    private final ProyectoService proyectoService;

    // Crear un proyecto
    @PostMapping
    public ProyectoDTO crearProyecto(@RequestBody ProyectoDTO proyectoDTO) {
        return proyectoService.crearProyecto(proyectoDTO);
    }

    // Listar proyectos por usuario
    @GetMapping("/usuario/{usuarioId}")
    public List<ProyectoDTO> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return proyectoService.obtenerProyectosPorUsuario(usuarioId);
    }

    // Obtener proyecto por ID
    @GetMapping("/{id}")
    public ProyectoDTO obtenerPorId(@PathVariable Long id) {
        return proyectoService.obtenerProyectoPorId(id);
    }

    // Eliminar proyecto por ID
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        proyectoService.eliminarProyecto(id);
    }
}
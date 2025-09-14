package TaskPathBackend.controller;

import TaskPathBackend.dto.TareaDTO;
import TaskPathBackend.service.TareaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tareas")
public class TareaController {

    private final TareaService tareaService;

    public TareaController(TareaService tareaService) {
        this.tareaService = tareaService;
    }

    @PostMapping
    public TareaDTO crear(@RequestBody TareaDTO tareaDTO) {
        return tareaService.crearTarea(tareaDTO);
    }

    @GetMapping("/proyecto/{proyectoId}")
    public List<TareaDTO> obtenerPorProyecto(@PathVariable Long proyectoId) {
        return tareaService.obtenerTareasPorProyecto(proyectoId);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<TareaDTO> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return tareaService.obtenerTareasPorUsuario(usuarioId);
    }

    @PutMapping("/{id}/estado")
    public TareaDTO actualizarEstado(@PathVariable Long id, @RequestParam String estado) {
        return tareaService.actualizarEstado(id, estado);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        tareaService.eliminarTarea(id);
    }
}

package TaskPathBackend.controller;

import TaskPathBackend.dto.ProgramaDTO;
import TaskPathBackend.service.ProgramaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/programas")
@RequiredArgsConstructor
public class ProgramaController {

    private final ProgramaService programaService;

    @PostMapping
    public ProgramaDTO crear(@RequestBody ProgramaDTO dto) {
        return programaService.crearPrograma(dto);
    }

    @PostMapping("/bulk")
    public List<ProgramaDTO> crearProgramas(@RequestBody List<ProgramaDTO> dtos) {
        return programaService.crearProgramas(dtos);
    }

    @GetMapping
    public List<ProgramaDTO> listar() {
        return programaService.listarProgramas();
    }

    @GetMapping("/{id}")
    public ProgramaDTO obtenerPorId(@PathVariable Long id) {
        return programaService.obtenerProgramaPorId(id);
    }

    @PutMapping("/{id}")
    public ProgramaDTO actualizar(@PathVariable Long id, @RequestBody ProgramaDTO dto) {
        return programaService.actualizarPrograma(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        programaService.eliminarPrograma(id);
    }

    @GetMapping("/nombres")
    public List<String> obtenerNombres(
            @RequestParam Long idNivelFormacion,
            @RequestParam Long idUniversidad,
            @RequestParam String estado) {
        return programaService.obtenerNombresProgramas(idNivelFormacion, idUniversidad, estado);
    }
}

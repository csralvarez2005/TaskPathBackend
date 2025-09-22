package TaskPathBackend.service;

import TaskPathBackend.dto.ProgramaDTO;

import java.util.List;

public interface ProgramaService {
    ProgramaDTO crearPrograma(ProgramaDTO dto);
    List<ProgramaDTO> listarProgramas();
    ProgramaDTO obtenerProgramaPorId(Long id);
    ProgramaDTO actualizarPrograma(Long id, ProgramaDTO dto);
    void eliminarPrograma(Long id);
    List<ProgramaDTO> crearProgramas(List<ProgramaDTO> dtos);
    List<String> obtenerNombresProgramas(Long idNivelFormacion, Long idUniversidad, String estado);
}

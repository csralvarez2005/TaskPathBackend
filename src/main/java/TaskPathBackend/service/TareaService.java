package TaskPathBackend.service;

import TaskPathBackend.dto.TareaDTO;

import java.util.List;

public interface TareaService {

    TareaDTO crearTarea(TareaDTO tareaDTO);
    List<TareaDTO> obtenerTareasPorProyecto(Long proyectoId);
    List<TareaDTO> obtenerTareasPorUsuario(Long usuarioId);
    TareaDTO actualizarEstado(Long id, String nuevoEstado);
    void eliminarTarea(Long id);
}

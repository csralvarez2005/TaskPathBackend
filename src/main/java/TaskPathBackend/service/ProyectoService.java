package TaskPathBackend.service;

import TaskPathBackend.dto.ProyectoDTO;

import java.util.List;

public interface ProyectoService {

    ProyectoDTO crearProyecto(ProyectoDTO proyectoDTO);
    List<ProyectoDTO> obtenerProyectosPorUsuario(Long usuarioId);
    ProyectoDTO obtenerProyectoPorId(Long id);
    void eliminarProyecto(Long id);
}

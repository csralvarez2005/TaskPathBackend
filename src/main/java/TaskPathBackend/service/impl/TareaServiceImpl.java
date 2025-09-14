package TaskPathBackend.service.impl;


import TaskPathBackend.dto.TareaDTO;
import TaskPathBackend.entity.Tarea;
import TaskPathBackend.mapper.TareaMapper;
import TaskPathBackend.repository.TareaRepository;
import TaskPathBackend.service.SequenceGeneratorService;
import TaskPathBackend.service.TareaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TareaServiceImpl implements TareaService {

    private final TareaRepository tareaRepository;
    private final SequenceGeneratorService sequenceGenerator;
    private final TareaMapper tareaMapper;

    @Override
    public TareaDTO crearTarea(TareaDTO dto) {
        Tarea tarea = tareaMapper.toEntity(dto);
        tarea.setId(sequenceGenerator.generateSequence(Tarea.SEQUENCE_NAME));
        tarea = tareaRepository.save(tarea);
        return tareaMapper.toDto(tarea);
    }

    @Override
    public List<TareaDTO> obtenerTareasPorProyecto(Long proyectoId) {
        return tareaMapper.toDtoList(tareaRepository.findByProyectoId(proyectoId));
    }

    @Override
    public List<TareaDTO> obtenerTareasPorUsuario(Long usuarioId) {
        return tareaMapper.toDtoList(tareaRepository.findByUsuarioId(usuarioId));
    }

    @Override
    public TareaDTO actualizarEstado(Long id, String nuevoEstado) {
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con id " + id));
        tarea.setEstado(nuevoEstado);
        tarea = tareaRepository.save(tarea);
        return tareaMapper.toDto(tarea);
    }

    @Override
    public void eliminarTarea(Long id) {
        tareaRepository.deleteById(id);
    }
}

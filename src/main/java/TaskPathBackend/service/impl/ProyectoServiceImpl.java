package TaskPathBackend.service.impl;


import TaskPathBackend.dto.ProyectoDTO;
import TaskPathBackend.entity.Proyecto;
import TaskPathBackend.mapper.ProyectoMapper;
import TaskPathBackend.repository.ProyectoRepository;
import TaskPathBackend.service.ProyectoService;
import TaskPathBackend.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProyectoServiceImpl implements ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private SequenceGeneratorService sequenceGenerator;

    @Autowired
    private ProyectoMapper proyectoMapper;

    @Override
    public ProyectoDTO crearProyecto(ProyectoDTO dto) {
        Proyecto proyecto = proyectoMapper.toEntity(dto);

        if (proyecto.getId() == null) {
            proyecto.setId(sequenceGenerator.generateSequence("proyectos_sequence"));
        }

        Proyecto saved = proyectoRepository.save(proyecto);
        return proyectoMapper.toDTO(saved);
    }

    @Override
    public List<ProyectoDTO> obtenerProyectosPorUsuario(Long usuarioId) {
        return proyectoMapper.toDTOList(proyectoRepository.findByUsuarioId(usuarioId));
    }

    @Override
    public ProyectoDTO obtenerProyectoPorId(Long id) {
        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado con id " + id));
        return proyectoMapper.toDTO(proyecto);
    }

    @Override
    public void eliminarProyecto(Long id) {
        proyectoRepository.deleteById(id);
    }
}
package TaskPathBackend.mapper;

import TaskPathBackend.dto.ProyectoDTO;
import TaskPathBackend.entity.Proyecto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProyectoMapper {

    ProyectoMapper INSTANCE = Mappers.getMapper(ProyectoMapper.class);
    ProyectoDTO toDTO(Proyecto proyecto);
    Proyecto toEntity(ProyectoDTO dto);
    List<ProyectoDTO> toDTOList(List<Proyecto> proyectos);
}

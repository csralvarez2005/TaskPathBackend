package TaskPathBackend.mapper;


import TaskPathBackend.dto.ProgramaDTO;
import TaskPathBackend.entity.Programa;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProgramaMapper {
    ProgramaDTO toDTO(Programa entity);
    Programa toEntity(ProgramaDTO dto);
    List<ProgramaDTO> toDTOList(List<Programa> entities);
}

package TaskPathBackend.mapper;



import TaskPathBackend.dto.ListaValorDTO;
import TaskPathBackend.entity.ListaValor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ListaValorMapper {

    ListaValorMapper INSTANCE = Mappers.getMapper(ListaValorMapper.class);

    ListaValorDTO toDTO(ListaValor entity);

    ListaValor toEntity(ListaValorDTO dto);
}

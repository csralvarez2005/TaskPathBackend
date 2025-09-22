package TaskPathBackend.mapper;


import TaskPathBackend.dto.PagedResponseDTO;
import org.springframework.data.domain.Page;

public class PagedResponseMapper {

    public static <T> PagedResponseDTO<T> toDTO(Page<T> page) {
        return new PagedResponseDTO<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }
}

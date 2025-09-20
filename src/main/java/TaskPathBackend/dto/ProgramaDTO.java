package TaskPathBackend.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgramaDTO {
    private Long id;
    private String codigoPrograma;
    private String estado;
    private Long idFacultad;
    private Long idJornada;
    private String nombre;
    private Long idNivelFormacion;
    private Long idUniversidad;
    private String tipoPrograma;
}
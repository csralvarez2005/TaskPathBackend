package TaskPathBackend.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "programas")
public class Programa {

    @Id
    private Long id;
    private String codigoPrograma;
    private String estado;
    private Long idFacultad;
    private Long idJornada;
    private String nombre;
    private Long idNivelFormacion;
    private Long idUniversidad;
    private String tipoPrograma;
    public static final String SEQUENCE_NAME = "programas_sequence";
}

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
    private Long id;                  // PK
    private String codigoPrograma;    // varchar(255)
    private String estado;            // varchar(255)
    private Long idFacultad;          // numeric(19)
    private Long idJornada;           // numeric(19)
    private String nombre;            // varchar(255)
    private Long idNivelFormacion;    // numeric(19)
    private Long idUniversidad;       // numeric(19)
    private String tipoPrograma;      // varchar(255)

    public static final String SEQUENCE_NAME = "programas_sequence";
}

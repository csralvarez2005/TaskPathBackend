package TaskPathBackend.entity;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "proyectos")
public class Proyecto {

    @Id
    private Long id;
    private String nombre;
    private String descripcion;
    private Long usuarioId;
}
package TaskPathBackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cargos")
public class Cargo {

    @Id
    private Long id;
    private String codigoCargo;
    private String estado;
    private String nombreCargo;
}

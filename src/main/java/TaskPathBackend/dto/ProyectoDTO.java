package TaskPathBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data                   // genera getters, setters, equals, hashCode, toString
@NoArgsConstructor      // constructor vacío
@AllArgsConstructor     // constructor con todos los campos
public class ProyectoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Long usuarioId;
}
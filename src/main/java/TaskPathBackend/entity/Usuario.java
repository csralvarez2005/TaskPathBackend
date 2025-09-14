package TaskPathBackend.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "usuarios")
public class Usuario {

    @Id
    private Long id;
    private String nombre;
    private String apellidoFuncionario;
    private String nombreFuncionario;
    private String email;
    private String password;
    private String rol;
    private String celular;
    private String telefono;
    private String direccion;
    private String estado;
    private LocalDateTime fechaNacimiento;
    private Long idCargo;
    private Long idEstadoCivil;
    private Long idGenero;
    private Long idTipoIdentificacion;
    private String identificacion;
    private String idFoto;
    private String nombreArchivoFoto;
}
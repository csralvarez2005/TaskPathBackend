package TaskPathBackend.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "usuarios")
public class Usuario {

    @Id
    private Long id;

    private String apellidoFuncionario;
    private String nombreFuncionario;
    private String email;
    private String username;
    private String password;
    private String rol;
    private String celular;
    private String direccion;
    private String estado = "ACTIVO";
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fechaNacimiento;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date fechaCreacion = new Date();
    @DBRef
    private Cargo cargo;
    @DBRef
    private ListaValor estadoCivil;
    @DBRef
    private ListaValor genero;
    @DBRef
    private ListaValor tipoIdentificacion;
    private String identificacion;
    private String idFoto;
    private String nombreArchivoFoto;
}

package TaskPathBackend.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "lista_valor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListaValor {

    @Id
    private long codigo;
    private String categoria;
    private String estado;
    private String referencia;
    private String valor;
}

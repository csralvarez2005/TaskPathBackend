package TaskPathBackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tareas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tarea {

    @Id
    private Long id;
    private String titulo;
    private String descripcion;
    private String estado;      // Ej: "Pendiente", "En progreso", "Completada"
    private Long proyectoId;    // Relación con Proyecto
    private Long usuarioId;     // Usuario asignado (opcional)

    // 🔹 Constante para secuencia
    public static final String SEQUENCE_NAME = "tareas_sequence";
}
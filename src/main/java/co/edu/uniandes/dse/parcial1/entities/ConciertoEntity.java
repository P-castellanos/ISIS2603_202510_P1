package co.edu.uniandes.dse.parcial1.entities;

import java.time.LocalDateTime;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class ConciertoEntity extends BaseEntity {

    private String nombre;
    private String nombreArtista;
    private Long presupuesto;
    private String lugar;
    private LocalDateTime fecha;
    private int capacidad_aforo;

    @ManyToOne
    private EstadioEntity estadio;

}

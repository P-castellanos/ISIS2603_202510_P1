package co.edu.uniandes.dse.parcial1.entities;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;


@Data
@Entity
public class EstadioEntity extends BaseEntity 
{

    private String nombre;
    private String ciudad;
    private int capacidad_Maxima;
    private Long precioAlquiler;

    @OneToMany(mappedBy = "estadio")
    private List<ConciertoEntity> conciertos;
}

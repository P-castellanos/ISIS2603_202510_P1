package co.edu.uniandes.dse.parcial1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.uniandes.dse.parcial1.entities.Concierto;
import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EstadioService 
{

    @Autowired
    ConciertoRepository conciertoR;
    @Autowired
    EstadioRepository estaioR;

    public  EstadioService creaEstadio(EstadioEntity estadioEntity) throws IllegalOperationException
    {
        int len_c = estadioEntity.getCiudad().length();
        if (len_c < 3)
        {
            throw new IllegalOperationException("Ciudad muy corta");
        }
        if (estadioEntity.getCapacidad_Maxima() <= 1000) {
            throw new IllegalOperationException("La capacidad del estadio debe ser superior a 1000 personas");
        }

        if (estadioEntity.getPrecioAlquiler() <= 100000) {
            throw new IllegalOperationException("El precio de alquiler debe ser superior a 100000 dólares");
        }

        return estadioR.save(estadioEntity);
    }


}

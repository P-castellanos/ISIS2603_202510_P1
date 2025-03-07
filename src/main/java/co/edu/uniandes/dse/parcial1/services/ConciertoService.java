package co.edu.uniandes.dse.parcial1.services;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.Concierto;
import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.repositories.*;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class ConciertoService {

    @Autowired
    EstadioRepository estadioR;
    
    @Autowired
    ConciertoRepository conciertoR;
    // Complete
    public ConciertoEntity createConcierto(ConciertoEntity concierto) throws IllegalOperationException
    {
        log.info("Inicia proceso de creación del concierto");
        LocalDateTime fecha_actual = LocalDateTime.now();
        LocalDateTime fecha_concierto = concierto.getFecha();


        if (Duration.between(fecha_actual, fecha_concierto).isNegative())
        {
            throw new IllegalOperationException("Fecha pasada");

        }
        int cap = concierto.getCapacidad_aforo();
        if (cap < 10)
        {
            throw new IllegalOperationException("Capacidad < 10");

        }
        long prep = concierto.getPresupuesto();
        if (prep < 1000)
        {
            throw new IllegalOperationException("Muy pobre");
        } 
        EstadioEntity estadioEntity = estadioR.findByName(concierto.getEstadio().getNombre());
        concierto.setEstadio(estadioEntity.get())
        return conciertoR.save(concierto);
    }

}

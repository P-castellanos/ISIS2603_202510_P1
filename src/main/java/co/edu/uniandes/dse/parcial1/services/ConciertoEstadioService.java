package co.edu.uniandes.dse.parcial1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;
import co.edu.uniandes.dse.parcial1.repositories.EstadioRepository;
import lombok.extern.slf4j.Slf4j;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Service
public class ConciertoEstadioService 
{

    @Autowired
    private ConciertoRepository conciertoR;

    @Autowired
    private EstadioRepository estadioR;

    public ConciertoEntity asociarConciertoAEstadio(Long conciertoId, Long estadioId) throws EntityNotFoundException, IllegalOperationException {
        ConciertoEntity conciertoEntity = conciertoR.findById(conciertoId)
                .orElseThrow(() -> new EntityNotFoundException("Concierto no encontrado"));

        EstadioEntity estadioEntity = estadioR.findById(estadioId)
                .orElseThrow(() -> new EntityNotFoundException("Estadio no encontrado"));

        // Validar que la capacidad del concierto no supere la capacidad del estadio
        if (conciertoEntity.getCapacidad_aforo() > estadioEntity.getCapacidad_Maxima()) {
            throw new IllegalOperationException("La capconcierto > cap del estadio");
        }

        // Validar que el precio de alquiler del estadio no supere el presupuesto del concierto
        if (estadioEntity.getPrecioAlquiler() > conciertoEntity.getPresupuesto()) {
            throw new IllegalOperationException("El precio de alquiler  supera el presupuesto:(");
        }

        // Validar que siempre exista un tiempo mínimo de 2 días entre los conciertos asociados a un estadio
        List<ConciertoEntity> conciertosEnEstadio = conciertoR.findByName(estadioEntity.getNombre());
        for (ConciertoEntity concierto : conciertosEnEstadio) {
            long daysBetween = ChronoUnit.DAYS.between(concierto.getFecha(), conciertoEntity.getFecha());
            if (Math.abs(daysBetween) < 2) {
                throw new IllegalOperationException("Debe existir un tiempo mínimo de 2 días entre los conciertos asociados a un estadio");
            }
        }

        conciertoEntity.setEstadio(estadioEntity);
        return conciertoR.save(conciertoEntity);
    }
}

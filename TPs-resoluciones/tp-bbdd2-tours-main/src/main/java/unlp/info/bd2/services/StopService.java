package unlp.info.bd2.services;

import unlp.info.bd2.model.Stop;
import unlp.info.bd2.utils.ToursException;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicios para operaciones sobre paradas.
 * Gestiona creación, búsqueda, listado y eliminación de paradas.
 */
public interface StopService {

    Stop createStop(String name, String description) throws ToursException;

    Optional<Stop> getStopById(Long id) throws ToursException;

    List<Stop> getAllStops() throws ToursException;

    List<Stop> getStopsByNameStart(String prefix) throws ToursException;

    void deleteStop(Long stopId) throws ToursException;
}

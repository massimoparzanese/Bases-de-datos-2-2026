package unlp.info.bd2.services.Impl;

import jakarta.transaction.Transactional;
import unlp.info.bd2.model.Stop;
import unlp.info.bd2.repositories.Impl.StopRepositoryImpl;
import unlp.info.bd2.services.StopService;
import unlp.info.bd2.utils.ToursException;

import java.util.List;
import java.util.Optional;

public class StopServiceImpl implements StopService {

    private final StopRepositoryImpl stopRepository;

    public StopServiceImpl(StopRepositoryImpl stopRepository) {
        this.stopRepository = stopRepository;
    }

    @Override
    @Transactional(rollbackOn = ToursException.class)
    public Stop createStop(String name, String description) throws ToursException {
        try {
            return stopRepository.save(new Stop(name, description));
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo crear la parada");
        }
    }

    @Override
    @Transactional(rollbackOn = ToursException.class)
    public Optional<Stop> getStopById(Long id) throws ToursException {
        try {
            return stopRepository.findById(id);
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo buscar la parada por id");
        }
    }

    @Override
    @Transactional(rollbackOn = ToursException.class)
    public List<Stop> getAllStops() throws ToursException {
        try {
            return stopRepository.findAll();
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo listar las paradas");
        }
    }

    @Override
    @Transactional(rollbackOn = ToursException.class)
    public List<Stop> getStopsByNameStart(String prefix) throws ToursException {
        try {
            return stopRepository.findAll().stream()
                    .filter(s -> s.getName() != null && s.getName().startsWith(prefix))
                    .toList();
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo buscar paradas por prefijo");
        }
    }

    @Override
    @Transactional(rollbackOn = ToursException.class)
    public void deleteStop(Long stopId) throws ToursException {
        try {
            Stop stop = stopRepository.findById(stopId)
                    .orElseThrow(() -> new ToursException("No existe una parada con id " + stopId));
            if (stop.getRouteList() != null && !stop.getRouteList().isEmpty()) {
                throw new ToursException("No se puede eliminar una parada asociada a rutas");
            }
            stopRepository.delete(stop);
        } catch (ToursException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo eliminar la parada");
        }
    }
}

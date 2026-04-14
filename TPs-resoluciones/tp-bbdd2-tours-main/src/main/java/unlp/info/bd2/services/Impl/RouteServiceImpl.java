package unlp.info.bd2.services.Impl;

import jakarta.transaction.Transactional;
import unlp.info.bd2.model.Purchase;
import unlp.info.bd2.model.Route;
import unlp.info.bd2.model.Stop;
import unlp.info.bd2.repositories.Impl.PurchaseRepositoryImpl;
import unlp.info.bd2.repositories.Impl.RouteRepositoryImpl;
import unlp.info.bd2.services.RouteService;
import unlp.info.bd2.utils.ToursException;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de servicios para operaciones sobre rutas.
 */
public class RouteServiceImpl implements RouteService {

    private final RouteRepositoryImpl routeRepository;
    private final PurchaseRepositoryImpl purchaseRepository;

    public RouteServiceImpl(RouteRepositoryImpl routeRepository,
            PurchaseRepositoryImpl purchaseRepository) {
        this.routeRepository = routeRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    @Transactional(rollbackOn = ToursException.class)
    public Route createRoute(String name, float price, float totalKm, int maxNumberUsers, List<Stop> stops) throws ToursException {
        try {
            Route route = new Route(name, price, totalKm, maxNumberUsers);
            if (stops != null) {
                route.setStops(stops);
            }
            return routeRepository.save(route);
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo crear la ruta");
        }
    }

    @Override
    @Transactional(rollbackOn = ToursException.class)
    public Optional<Route> getRouteById(Long id) throws ToursException {
        try {
            return routeRepository.findById(id);
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo buscar la ruta por id");
        }
    }

    @Override
    @Transactional(rollbackOn = ToursException.class)
    public List<Route> getAllRoutes() throws ToursException {
        try {
            return routeRepository.findAll();
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo listar las rutas");
        }
    }

    @Override
    @Transactional(rollbackOn = ToursException.class)
    public Route updateRoute(Route route) throws ToursException {
        if (route == null || route.getId() == null) {
            throw new ToursException("La ruta a actualizar debe tener id");
        }

        try {
            return routeRepository.save(route);
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo actualizar la ruta");
        }
    }

    @Override
    @Transactional(rollbackOn = ToursException.class)
    public void deleteRoute(Long routeId) throws ToursException {
        try {
            Route route = routeRepository.findById(routeId)
                    .orElseThrow(() -> new ToursException("No existe una ruta con id " + routeId));
            routeRepository.delete(route);
        } catch (ToursException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo eliminar la ruta");
        }
    }

    @Override
    @Transactional(rollbackOn = ToursException.class)
    public void deleteRouteIfNoSales(Long routeId) throws ToursException {
        try {
            Route route = routeRepository.findById(routeId)
                    .orElseThrow(() -> new ToursException("No existe una ruta con id " + routeId));

            boolean hasSales = purchaseRepository.findAll()
                    .stream()
                    .map(Purchase::getRoute)
                    .filter(r -> r != null && r.getId() != null)
                    .anyMatch(r -> r.getId().equals(routeId));

            if (hasSales) {
                throw new ToursException("No puede eliminarse una ruta con compras asociadas");
            }

            routeRepository.delete(route);
        } catch (ToursException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo eliminar la ruta");
        }
    }
}

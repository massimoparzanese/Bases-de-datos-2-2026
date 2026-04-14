package unlp.info.bd2.services;

import unlp.info.bd2.model.Route;
import unlp.info.bd2.model.Stop;
import unlp.info.bd2.utils.ToursException;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicios para operaciones sobre rutas.
 * Gestiona creación, actualización, búsqueda y eliminación de rutas.
 */
public interface RouteService {

    Route createRoute(String name, float price, float totalKm, int maxNumberUsers, List<Stop> stops) throws ToursException;

    Optional<Route> getRouteById(Long id) throws ToursException;

    List<Route> getAllRoutes() throws ToursException;

    Route updateRoute(Route route) throws ToursException;

    void deleteRoute(Long routeId) throws ToursException;

    void deleteRouteIfNoSales(Long routeId) throws ToursException;
}

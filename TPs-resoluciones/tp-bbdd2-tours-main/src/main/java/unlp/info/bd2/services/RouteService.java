package unlp.info.bd2.services;

import unlp.info.bd2.dto.RouteSummaryDTO;
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

    void assignDriverByUsername(String username, Long routeId) throws ToursException;

    void assignTourGuideByUsername(String username, Long routeId) throws ToursException;

    List<Route> getRoutesBelowPrice(float price) throws ToursException;

    List<Route> getRoutesWithStop(Stop stop) throws ToursException;

    int getMaxStopOfRoutes() throws ToursException;

    List<Route> getRoutesNotSell() throws ToursException;

    List<Route> getTop3RoutesWithMaxRating() throws ToursException;
    
    List<RouteSummaryDTO> getRouteSummaries() throws ToursException;
}

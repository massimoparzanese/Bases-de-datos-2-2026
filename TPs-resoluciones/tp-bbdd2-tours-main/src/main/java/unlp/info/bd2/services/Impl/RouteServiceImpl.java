package unlp.info.bd2.services.Impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import unlp.info.bd2.dto.RouteSummaryDTO;
import unlp.info.bd2.model.DriverUser;
import unlp.info.bd2.model.Route;
import unlp.info.bd2.model.Stop;
import unlp.info.bd2.model.TourGuideUser;
import unlp.info.bd2.model.User;
import unlp.info.bd2.repositories.PurchaseRepository;
import unlp.info.bd2.repositories.RouteRepository;
import unlp.info.bd2.repositories.UserRepository;
import unlp.info.bd2.services.RouteService;
import unlp.info.bd2.utils.ToursException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación de servicios para operaciones sobre rutas.
 */
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;
    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;

    public RouteServiceImpl(RouteRepository routeRepository,
            PurchaseRepository purchaseRepository,
            UserRepository userRepository) {
        this.routeRepository = routeRepository;
        this.purchaseRepository = purchaseRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(rollbackFor = ToursException.class)
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
    @Transactional(readOnly = true)
    public Optional<Route> getRouteById(Long id) throws ToursException {
        try {
            return routeRepository.findById(id);
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo buscar la ruta por id");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Route> getAllRoutes() throws ToursException {
        try {
            return (List<Route>) routeRepository.findAll();
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo listar las rutas");
        }
    }

    @Override
    @Transactional(rollbackFor = ToursException.class)
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
    @Transactional(rollbackFor = ToursException.class)
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
    @Transactional(rollbackFor = ToursException.class)
    public void deleteRouteIfNoSales(Long routeId) throws ToursException {
        try {
            Route route = routeRepository.findById(routeId)
                    .orElseThrow(() -> new ToursException("No existe una ruta con id " + routeId));

                boolean hasSales = purchaseRepository.existsByRouteId(routeId);

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

    @Override
    @Transactional(readOnly = true)
    public List<Route> getRoutesBelowPrice(float price) throws ToursException {
        try {
            return routeRepository.findByPriceLessThanOrderByNameAsc(price);
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo obtener las rutas por precio");
        }
    }

    @Transactional(readOnly = true)
    public List<Route> getRoutesWithStop(Stop stop) throws ToursException {
        try {
            return routeRepository.getRoutesWithStop(stop);
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo obtener las rutas con el stop indicado");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public int getMaxStopOfRoutes() throws ToursException {
        try {
            return routeRepository.getMaxStopOfRoutes();
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo obtener la maxima cantidad de paradas por ruta");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Route> getRoutesNotSell() throws ToursException {
        try {
            return routeRepository.getRoutesNotSell();
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo obtener las rutas sin ventas");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Route> getTop3RoutesWithMaxRating() throws ToursException {
        try {
            return routeRepository.getTop3RoutesWithMaxRating(PageRequest.of(0, 3));
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo obtener el top 3 de rutas con mayor rating");
        }
    }

    @Transactional(rollbackFor = ToursException.class)
    public void assignDriverByUsername(String username, Long routeId) throws ToursException {
        try {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ToursException("No pudo realizarse la asignación"));
            if (!(user instanceof DriverUser driverUser)) {
                throw new ToursException("No pudo realizarse la asignación");
            }

            Route route = routeRepository.findById(routeId)
                    .orElseThrow(() -> new ToursException("No pudo realizarse la asignación"));

            if (driverUser.getRoutes() == null) {
                driverUser.setRoutes(new ArrayList<>());
            }
            if (route.getDriverList() == null) {
                route.setDriverList(new ArrayList<>());
            }

            if (!driverUser.getRoutes().contains(route)) {
                driverUser.getRoutes().add(route);
            }
            if (!route.getDriverList().contains(driverUser)) {
                route.getDriverList().add(driverUser);
            }

            userRepository.save(driverUser);
            routeRepository.save(route);
        } catch (ToursException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            throw new ToursException("No pudo realizarse la asignación");
        }
    }

    @Transactional(rollbackFor = ToursException.class)
    public void assignTourGuideByUsername(String username, Long routeId) throws ToursException {
        try {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ToursException("No pudo realizarse la asignación"));
            if (!(user instanceof TourGuideUser tourGuideUser)) {
                throw new ToursException("No pudo realizarse la asignación");
            }

            Route route = routeRepository.findById(routeId)
                    .orElseThrow(() -> new ToursException("No pudo realizarse la asignación"));

            if (tourGuideUser.getRoutes() == null) {
                tourGuideUser.setRoutes(new ArrayList<>());
            }
            if (route.getTourGuideList() == null) {
                route.setTourGuideList(new ArrayList<>());
            }

            if (!tourGuideUser.getRoutes().contains(route)) {
                tourGuideUser.getRoutes().add(route);
            }
            if (!route.getTourGuideList().contains(tourGuideUser)) {
                route.getTourGuideList().add(tourGuideUser);
            }

            userRepository.save(tourGuideUser);
            routeRepository.save(route);
        } catch (ToursException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            throw new ToursException("No pudo realizarse la asignación");
        }

    
    }
    public List<RouteSummaryDTO> getRouteSummaries() {
         return routeRepository.getRouteSummariesRaw().stream()
                .map(row -> new RouteSummaryDTO(
                        (String) row[0],     // name
                        (Long) row[1],       // count
                        (Double) row[2]      // avg
                ))
                .toList(); 
    }
    
}

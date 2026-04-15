package unlp.info.bd2.services;

import unlp.info.bd2.model.*;
import unlp.info.bd2.utils.ToursException;

import java.util.List;

/**
 * Interfaz para operaciones de servicio de tours con transacciones.
 * Define métodos para crear entidades, actualizar precios, gestionar compras y rutas.
 */
public interface TourService {

    String createAllEntities(
            List<Object[]> users,
            List<Object[]> suppliers,
            List<Object[]> services,
            List<Object[]> routes,
            List<Object[]> stops
    ) throws ToursException;

    Service updateServicePrice(Long serviceId, float newPrice) throws ToursException;

    ItemService addItemToPurchase(Long purchaseId, Long serviceId, int quantity) throws ToursException;


    void deleteRouteIfNoSales(Long routeId) throws ToursException;
}

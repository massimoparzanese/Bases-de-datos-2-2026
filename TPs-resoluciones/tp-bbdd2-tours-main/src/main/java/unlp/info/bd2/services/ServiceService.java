package unlp.info.bd2.services;

import unlp.info.bd2.model.Service;
import unlp.info.bd2.model.Supplier;
import unlp.info.bd2.utils.ToursException;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicios para operaciones sobre servicios (servicios turísticos).
 * Gestiona creación, actualización, búsqueda y eliminación de servicios.
 */
public interface ServiceService {

    Service createService(String name, float price, String description, Supplier supplier) throws ToursException;

    Optional<Service> getServiceById(Long id) throws ToursException;

    List<Service> getAllServices() throws ToursException;

    List<Service> getServicesBySupplier(Long supplierId) throws ToursException;

    Service updateService(Service service) throws ToursException;

    Service updateServicePrice(Long serviceId, float newPrice) throws ToursException;

    void deleteService(Long serviceId) throws ToursException;

    Service getMostDemandedService() throws ToursException;
}

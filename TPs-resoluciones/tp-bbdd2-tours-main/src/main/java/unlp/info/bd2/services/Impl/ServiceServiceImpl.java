package unlp.info.bd2.services.Impl;

import jakarta.transaction.Transactional;
import unlp.info.bd2.model.Service;
import unlp.info.bd2.model.Supplier;
import unlp.info.bd2.repositories.Impl.ServiceRepositoryImpl;
import unlp.info.bd2.repositories.Impl.SupplierRepositoryImpl;
import unlp.info.bd2.services.ServiceService;
import unlp.info.bd2.utils.ToursException;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de servicios para operaciones sobre servicios (servicios turísticos).
 */
public class ServiceServiceImpl implements ServiceService {


    private final ServiceRepositoryImpl serviceRepository;

    private final SupplierRepositoryImpl supplierRepository;

    public ServiceServiceImpl(ServiceRepositoryImpl serviceRepository, SupplierRepositoryImpl supplierRepository) {
        this.serviceRepository = serviceRepository;
        this.supplierRepository = supplierRepository;
    }

    @Override
    @Transactional(rollbackOn = ToursException.class)
    public Service createService(String name, float price, String description, Supplier supplier) throws ToursException {
        if (supplier == null) {
            throw new ToursException("El proveedor es obligatorio para crear un servicio");
        }
        try {
            Supplier managedSupplier = supplier;
            if (supplier.getId() != null) {
                managedSupplier = supplierRepository.findById(supplier.getId())
                        .orElseThrow(() -> new ToursException("No existe el proveedor indicado"));
            }

            Service service = new Service(name, price, description, managedSupplier);
            Service savedService = serviceRepository.save(service);

            if (managedSupplier.getServices() != null) {
                managedSupplier.getServices().add(savedService);
                supplierRepository.save(managedSupplier);
            }

            return savedService;
        } catch (ToursException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo crear el servicio");
        }
    }

    @Override
    @Transactional(rollbackOn = ToursException.class)
    public Optional<Service> getServiceById(Long id) throws ToursException {
        try {
            return serviceRepository.findById(id);
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo buscar el servicio por id");
        }
    }

    @Override
    @Transactional(rollbackOn = ToursException.class)
    public List<Service> getAllServices() throws ToursException {
        try {
            return serviceRepository.findAll();
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo listar los servicios");
        }
    }

    @Override
    @Transactional(rollbackOn = ToursException.class)
    public List<Service> getServicesBySupplier(Long supplierId) throws ToursException {
        try {
            return serviceRepository.findAll()
                    .stream()
                    .filter(s -> s.getSupplier() != null
                            && s.getSupplier().getId() != null
                            && s.getSupplier().getId().equals(supplierId))
                    .toList();
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo obtener los servicios del proveedor");
        }
    }

    @Override
    @Transactional(rollbackOn = ToursException.class)
    public Service updateService(Service service) throws ToursException {
        if (service == null || service.getId() == null) {
            throw new ToursException("El servicio a actualizar debe tener id");
        }

        try {
            return serviceRepository.save(service);
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo actualizar el servicio");
        }
    }

    @Override
    @Transactional(rollbackOn = ToursException.class)
    public Service updateServicePrice(Long serviceId, float newPrice) throws ToursException {
        if (newPrice < 0) {
            throw new ToursException("El nuevo precio no puede ser negativo");
        }

        try {
            Service service = serviceRepository.findById(serviceId)
                    .orElseThrow(() -> new ToursException("No existe el producto"));
            service.setPrice(newPrice);
            return serviceRepository.save(service);
        } catch (ToursException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo actualizar el precio del servicio");
        }
    }

    @Override
    @Transactional(rollbackOn = ToursException.class)
    public void deleteService(Long serviceId) throws ToursException {
        try {
            Service service = serviceRepository.findById(serviceId)
                    .orElseThrow(() -> new ToursException("No existe un servicio con id " + serviceId));

            if (service.getItemServiceList() != null && !service.getItemServiceList().isEmpty()) {
                throw new ToursException("No se puede eliminar un servicio con items asociados");
            }

            serviceRepository.delete(service);
        } catch (ToursException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo eliminar el servicio");
        }
    }
}

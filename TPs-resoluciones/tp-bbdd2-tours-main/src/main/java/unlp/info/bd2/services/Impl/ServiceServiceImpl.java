package unlp.info.bd2.services.Impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import unlp.info.bd2.model.Service;
import unlp.info.bd2.model.Supplier;
import unlp.info.bd2.repositories.ServiceRepository;
import unlp.info.bd2.repositories.SupplierRepository;
import unlp.info.bd2.services.ServiceService;
import unlp.info.bd2.utils.ToursException;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de servicios para operaciones sobre servicios (servicios turísticos).
 */
public class ServiceServiceImpl implements ServiceService {


    private final ServiceRepository serviceRepository;

    private final SupplierRepository supplierRepository;

    public ServiceServiceImpl(ServiceRepository serviceRepository, SupplierRepository supplierRepository) {
        this.serviceRepository = serviceRepository;
        this.supplierRepository = supplierRepository;
    }

    @Override
    @Transactional(rollbackFor = ToursException.class)
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
    @Transactional(readOnly = true)
    public Optional<Service> getServiceById(Long id) throws ToursException {
        try {
            return serviceRepository.findById(id);
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo buscar el servicio por id");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Service> getAllServices() throws ToursException {
        try {
            return (List<Service>) serviceRepository.findAll();
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo listar los servicios");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Service> getServicesBySupplier(Long supplierId) throws ToursException {
        try {
            return serviceRepository.findBySupplierId(supplierId);
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo obtener los servicios del proveedor");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Service> getServiceByNameAndSupplierId(String name, Long supplierId) throws ToursException {
        try {
            return serviceRepository.findByNameAndSupplierId(name, supplierId)
                    .stream()
                    .findFirst();
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo obtener el servicio del proveedor");
        }
    }

    @Override
    @Transactional(rollbackFor = ToursException.class)
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
    @Transactional(rollbackFor = ToursException.class)
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
    @Transactional(rollbackFor = ToursException.class)
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

    @Override
    @Transactional(readOnly = true)
    public Service getMostDemandedService() throws ToursException {
        try {
            List<Service> result = serviceRepository.getMostDemandedService(PageRequest.of(0, 1));
            if (result.isEmpty()) {
                throw new ToursException("No hay servicios registrados");
            }
            return result.get(0);
        } catch (ToursException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo obtener el servicio mas demandado");
        }
    }
}

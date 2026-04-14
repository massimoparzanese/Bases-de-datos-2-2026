package unlp.info.bd2.services.Impl;

import jakarta.transaction.Transactional;
import unlp.info.bd2.model.Supplier;
import unlp.info.bd2.repositories.Impl.SupplierRepositoryImpl;
import unlp.info.bd2.services.SupplierService;
import unlp.info.bd2.utils.ToursException;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de servicios para operaciones sobre proveedores.
 */
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepositoryImpl supplierRepository;

    public SupplierServiceImpl(SupplierRepositoryImpl supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    @Transactional(rollbackOn = ToursException.class)
    public Supplier createSupplier(String businessName, String authorizationNumber) throws ToursException {
        try {
            Supplier supplier = new Supplier(businessName, authorizationNumber);
            return supplierRepository.save(supplier);
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo crear el proveedor");
        }
    }

    @Override
    @Transactional(rollbackOn = ToursException.class)
    public Optional<Supplier> getSupplierById(Long id) throws ToursException {
        try {
            return supplierRepository.findById(id);
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo buscar el proveedor por id");
        }
    }

    @Override
    @Transactional(rollbackOn = ToursException.class)
    public Optional<Supplier> getSupplierByAuthorizationNumber(String authorizationNumber) throws ToursException {
        try {
            return supplierRepository.findAll()
                    .stream()
                    .filter(s -> s.getAuthorizationNumber() != null
                            && s.getAuthorizationNumber().equals(authorizationNumber))
                    .findFirst();
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo buscar el proveedor por numero de autorizacion");
        }
    }

    @Override
    @Transactional(rollbackOn = ToursException.class)
    public List<Supplier> getAllSuppliers() throws ToursException {
        try {
            return supplierRepository.findAll();
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo listar los proveedores");
        }
    }

    @Override
    @Transactional(rollbackOn = ToursException.class)
    public Supplier updateSupplier(Supplier supplier) throws ToursException {
        if (supplier == null || supplier.getId() == null) {
            throw new ToursException("El proveedor a actualizar debe tener id");
        }
        try {
            return supplierRepository.save(supplier);
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo actualizar el proveedor");
        }
    }

    @Override
    @Transactional(rollbackOn = ToursException.class)
    public void deleteSupplier(Long supplierId) throws ToursException {
        try {
            Optional<Supplier> supplier = supplierRepository.findById(supplierId);
            if (supplier.isEmpty()) {
                throw new ToursException("No existe un proveedor con id " + supplierId);
            }
            if (supplier.get().getServices() != null && !supplier.get().getServices().isEmpty()) {
                throw new ToursException("No se puede eliminar un proveedor con servicios asociados");
            }
            supplierRepository.delete(supplier.get());
        } catch (ToursException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo eliminar el proveedor");
        }
    }
}

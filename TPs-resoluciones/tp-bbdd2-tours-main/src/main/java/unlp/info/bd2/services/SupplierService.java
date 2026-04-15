package unlp.info.bd2.services;

import unlp.info.bd2.model.Supplier;
import unlp.info.bd2.utils.ToursException;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicios para operaciones sobre proveedores.
 * Gestiona creación, actualización, búsqueda y eliminación de proveedores.
 */
public interface SupplierService {

    Supplier createSupplier(String businessName, String authorizationNumber) throws ToursException;

    Optional<Supplier> getSupplierById(Long id) throws ToursException;

    Optional<Supplier> getSupplierByAuthorizationNumber(String authorizationNumber) throws ToursException;

    List<Supplier> getAllSuppliers() throws ToursException;

    Supplier updateSupplier(Supplier supplier) throws ToursException;

    void deleteSupplier(Long supplierId) throws ToursException;

    List<Supplier> getTopNSuppliersInPurchases(int n) throws ToursException;
}

package unlp.info.bd2.repositories;

import unlp.info.bd2.model.Supplier;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends CrudRepository<Supplier, Long> {

    List<Supplier> getTopNSuppliersInPurchases(int n);
}
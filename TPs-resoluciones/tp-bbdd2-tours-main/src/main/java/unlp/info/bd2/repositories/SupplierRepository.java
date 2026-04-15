package unlp.info.bd2.repositories;

import unlp.info.bd2.model.Supplier;
import java.util.List;
public interface SupplierRepository extends BaseRepository<Supplier, Long> {

    List<Supplier> getTopNSuppliersInPurchases(int n);
}

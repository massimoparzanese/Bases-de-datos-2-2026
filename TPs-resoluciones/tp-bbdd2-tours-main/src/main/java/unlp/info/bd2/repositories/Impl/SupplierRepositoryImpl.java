package unlp.info.bd2.repositories.Impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import unlp.info.bd2.model.Supplier;
import unlp.info.bd2.repositories.SupplierRepository;

@Repository
public class SupplierRepositoryImpl extends AbstractHibernateRepository<Supplier, Long> implements SupplierRepository {

    public SupplierRepositoryImpl() {
        super(Supplier.class);
    }

    public List<Supplier> getTopNSuppliersInPurchases(int n) {
        try {
            return this.currentSession()
                .createQuery(
                    "select s from Supplier s " +
                        "left join s.services sv " +
                        "left join sv.itemServiceList isv " +
                        "left join isv.purchase p " +
                        "group by s " +
                        "order by count(distinct p.id) desc",
                    Supplier.class)
                    .setMaxResults(n)
                    .getResultList();
        } catch (RuntimeException ex) {
            log.error("Error buscando los proveedores mas vendidos", ex);
            throw new IllegalStateException("No se pudo buscar los proveedores mas vendidos", ex);
        }
    }
}

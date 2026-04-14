package unlp.info.bd2.repositories.Impl;

import org.springframework.stereotype.Repository;

import unlp.info.bd2.model.Supplier;
import unlp.info.bd2.repositories.SupplierRepository;

@Repository
public class SupplierRepositoryImpl extends AbstractHibernateRepository<Supplier, Long> implements SupplierRepository {

    public SupplierRepositoryImpl() {
        super(Supplier.class);
    }
}

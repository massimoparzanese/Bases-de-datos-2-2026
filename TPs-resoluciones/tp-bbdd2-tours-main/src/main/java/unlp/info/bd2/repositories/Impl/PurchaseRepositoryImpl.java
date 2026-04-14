package unlp.info.bd2.repositories.Impl;

import org.springframework.stereotype.Repository;

import unlp.info.bd2.model.Purchase;
import unlp.info.bd2.repositories.PurchaseRepository;

@Repository
public class PurchaseRepositoryImpl extends AbstractHibernateRepository<Purchase, Long> implements PurchaseRepository {

    public PurchaseRepositoryImpl() {
        super(Purchase.class);
    }
}

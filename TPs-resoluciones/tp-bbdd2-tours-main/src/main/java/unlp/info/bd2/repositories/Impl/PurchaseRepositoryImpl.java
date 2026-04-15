package unlp.info.bd2.repositories.Impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import unlp.info.bd2.model.Purchase;
import unlp.info.bd2.repositories.PurchaseRepository;

@Repository
public class PurchaseRepositoryImpl extends AbstractHibernateRepository<Purchase, Long> implements PurchaseRepository {

    public PurchaseRepositoryImpl() {
        super(Purchase.class);
    }

    @Override
    public List<Purchase> findAllByUsername(String username) {
        try {
            return this.currentSession()
                    .createQuery("from Purchase p where p.user.username = :username", Purchase.class)
                    .setParameter("username", username)
                    .getResultList();
        } catch (RuntimeException ex) {
            log.error("Error buscando compras de username {}", username, ex);
            throw new IllegalStateException("No se pudo buscar compras por username", ex);
        }
    }

    @Override
    public int getCountOfPurchasesBetweenDates(Date start, Date end) {
        try{
            Long count = this.currentSession()
                    .createQuery("select count(p) from Purchase p where p.date >= :start and p.date <= :end", Long.class)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .getSingleResult();
            return count.intValue();
        } catch (RuntimeException ex) {
            log.error("Error obteniendo conteo de compras entre fechas {} y {}", start, end, ex);
            throw new IllegalStateException("No se pudo obtener el conteo de compras entre fechas", ex);
        }
    }
}

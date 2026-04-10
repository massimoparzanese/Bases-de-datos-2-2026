package unlp.info.bd2.repositories.Impl;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import unlp.info.bd2.model.Purchase;
import unlp.info.bd2.repositories.PurchaseRepository;

@Repository
public class PurchaseRepositoryImpl implements PurchaseRepository {

    private static final Logger log = LoggerFactory.getLogger(PurchaseRepositoryImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    private Session currentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public Purchase save(Purchase entity) {
        try {
            this.currentSession().merge(entity);
            return entity;
        } catch (RuntimeException ex) {
            log.error("Error guardando Purchase", ex);
            throw new IllegalStateException("No se pudo guardar Purchase", ex);
        }
    }

    @Override
    public Optional<Purchase> findById(Long id) {
        try {
            Purchase purchase = this.currentSession().find(Purchase.class, id);
            return Optional.ofNullable(purchase);
        } catch (RuntimeException ex) {
            log.error("Error buscando Purchase por id {}", id, ex);
            throw new IllegalStateException("No se pudo buscar Purchase por id", ex);
        }
    }

    @Override
    public List<Purchase> findAll() {
        try {
            return this.currentSession()
                    .createQuery("from Purchase", Purchase.class)
                    .getResultList();
        } catch (RuntimeException ex) {
            log.error("Error listando Purchase", ex);
            throw new IllegalStateException("No se pudo listar Purchase", ex);
        }
    }

    @Override
    public void delete(Purchase entity) {
        try {
            this.currentSession().remove(this.currentSession().contains(entity) ? entity : this.currentSession().merge(entity));
        } catch (RuntimeException ex) {
            log.error("Error eliminando Purchase", ex);
            throw new IllegalStateException("No se pudo eliminar Purchase", ex);
        }
    }
}

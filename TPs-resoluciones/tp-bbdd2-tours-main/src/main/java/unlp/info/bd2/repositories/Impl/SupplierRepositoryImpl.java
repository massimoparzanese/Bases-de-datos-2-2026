package unlp.info.bd2.repositories.Impl;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import unlp.info.bd2.model.Supplier;
import unlp.info.bd2.repositories.SupplierRepository;

@Repository
public class SupplierRepositoryImpl implements SupplierRepository {

    private static final Logger log = LoggerFactory.getLogger(SupplierRepositoryImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    private Session currentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public Supplier save(Supplier entity) {
        try {
            this.currentSession().merge(entity);
            return entity;
        } catch (RuntimeException ex) {
            log.error("Error guardando Supplier", ex);
            throw new IllegalStateException("No se pudo guardar Supplier", ex);
        }
    }

    @Override
    public Optional<Supplier> findById(Long id) {
        try {
            Supplier supplier = this.currentSession().find(Supplier.class, id);
            return Optional.ofNullable(supplier);
        } catch (RuntimeException ex) {
            log.error("Error buscando Supplier por id {}", id, ex);
            throw new IllegalStateException("No se pudo buscar Supplier por id", ex);
        }
    }

    @Override
    public List<Supplier> findAll() {
        try {
            return this.currentSession()
                    .createQuery("from Supplier", Supplier.class)
                    .getResultList();
        } catch (RuntimeException ex) {
            log.error("Error listando Supplier", ex);
            throw new IllegalStateException("No se pudo listar Supplier", ex);
        }
    }

    @Override
    public void delete(Supplier entity) {
        try {
            this.currentSession().remove(this.currentSession().contains(entity) ? entity : this.currentSession().merge(entity));
        } catch (RuntimeException ex) {
            log.error("Error eliminando Supplier", ex);
            throw new IllegalStateException("No se pudo eliminar Supplier", ex);
        }
    }
}

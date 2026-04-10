package unlp.info.bd2.repositories.Impl;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import unlp.info.bd2.model.Service;
import unlp.info.bd2.repositories.ServiceRepository;

@Repository
public class ServiceRepositoryImpl implements ServiceRepository {

    private static final Logger log = LoggerFactory.getLogger(ServiceRepositoryImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    private Session currentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public Service save(Service entity) {
        try {
            this.currentSession().merge(entity);
            return entity;
        } catch (RuntimeException ex) {
            log.error("Error guardando Service", ex);
            throw new IllegalStateException("No se pudo guardar Service", ex);
        }
    }

    @Override
    public Optional<Service> findById(Long id) {
        try {
            Service service = this.currentSession().find(Service.class, id);
            return Optional.ofNullable(service);
        } catch (RuntimeException ex) {
            log.error("Error buscando Service por id {}", id, ex);
            throw new IllegalStateException("No se pudo buscar Service por id", ex);
        }
    }

    @Override
    public List<Service> findAll() {
        try {
            return this.currentSession()
                    .createQuery("from Service", Service.class)
                    .getResultList();
        } catch (RuntimeException ex) {
            log.error("Error listando Service", ex);
            throw new IllegalStateException("No se pudo listar Service", ex);
        }
    }

    @Override
    public void delete(Service entity) {
        try {
            this.currentSession().remove(this.currentSession().contains(entity) ? entity : this.currentSession().merge(entity));
        } catch (RuntimeException ex) {
            log.error("Error eliminando Service", ex);
            throw new IllegalStateException("No se pudo eliminar Service", ex);
        }
    }
}

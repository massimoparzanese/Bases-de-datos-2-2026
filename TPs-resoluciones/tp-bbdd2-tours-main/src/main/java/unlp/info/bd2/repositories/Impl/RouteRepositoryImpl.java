package unlp.info.bd2.repositories.Impl;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import unlp.info.bd2.model.Route;
import unlp.info.bd2.repositories.RouteRepository;

@Repository
public class RouteRepositoryImpl implements RouteRepository {

    private static final Logger log = LoggerFactory.getLogger(RouteRepositoryImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    private Session currentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public Route save(Route entity) {
        try {
            this.currentSession().merge(entity);
            return entity;
        } catch (RuntimeException ex) {
            log.error("Error guardando Route", ex);
            throw new IllegalStateException("No se pudo guardar Route", ex);
        }
    }

    @Override
    public Optional<Route> findById(Long id) {
        try {
            Route route = this.currentSession().find(Route.class, id);
            return Optional.ofNullable(route);
        } catch (RuntimeException ex) {
            log.error("Error buscando Route por id {}", id, ex);
            throw new IllegalStateException("No se pudo buscar Route por id", ex);
        }
    }

    @Override
    public List<Route> findAll() {
        try {
            return this.currentSession()
                    .createQuery("from Route", Route.class)
                    .getResultList();
        } catch (RuntimeException ex) {
            log.error("Error listando Route", ex);
            throw new IllegalStateException("No se pudo listar Route", ex);
        }
    }

    @Override
    public void delete(Route entity) {
        try {
            this.currentSession().remove(this.currentSession().contains(entity) ? entity : this.currentSession().merge(entity));
        } catch (RuntimeException ex) {
            log.error("Error eliminando Route", ex);
            throw new IllegalStateException("No se pudo eliminar Route", ex);
        }
    }
}

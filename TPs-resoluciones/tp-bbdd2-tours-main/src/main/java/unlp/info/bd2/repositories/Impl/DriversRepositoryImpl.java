package unlp.info.bd2.repositories.Impl;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import unlp.info.bd2.model.DriverUser;
import unlp.info.bd2.repositories.DriverUserRepository;

@Repository
public class DriversRepositoryImpl implements DriverUserRepository{

    private static final Logger log = LoggerFactory.getLogger(DriversRepositoryImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    private Session currentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public DriverUser save(DriverUser entity) {
        try {
            this.currentSession().merge(entity);
            return entity;
        } catch (RuntimeException ex) {
            log.error("Error guardando DriverUser", ex);
            throw new IllegalStateException("No se pudo guardar DriverUser", ex);
        }
    }

    @Override
    public Optional<DriverUser> findById(Long id) {
        try {
            DriverUser user = this.currentSession().find(DriverUser.class, id);
            return Optional.ofNullable(user);
        } catch (RuntimeException ex) {
            log.error("Error buscando DriverUser por id {}", id, ex);
            throw new IllegalStateException("No se pudo buscar DriverUser por id", ex);
        }
    }

    @Override
    public List<DriverUser> findAll() {
        try {
            return this.currentSession()
                    .createQuery("from DriverUser", DriverUser.class)
                    .getResultList();
        } catch (RuntimeException ex) {
            log.error("Error listando DriverUser", ex);
            throw new IllegalStateException("No se pudo listar DriverUser", ex);
        }
    }

    @Override
    public void delete(DriverUser entity) {
        try {
            this.currentSession().remove(this.currentSession().contains(entity) ? entity : this.currentSession().merge(entity));
        } catch (RuntimeException ex) {
            log.error("Error eliminando DriverUser", ex);
            throw new IllegalStateException("No se pudo eliminar DriverUser", ex);
        }
    }
    
}

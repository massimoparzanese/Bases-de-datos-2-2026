package unlp.info.bd2.repositories.Impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import unlp.info.bd2.repositories.BaseRepository;

public abstract class AbstractHibernateRepository<T, ID extends Serializable> implements BaseRepository<T, ID> {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private SessionFactory sessionFactory;

    private final Class<T> entityClass;

    protected AbstractHibernateRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected Session currentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    public T save(T entity) {
        try {
            return this.currentSession().merge(entity);
        } catch (RuntimeException ex) {
            log.error("Error guardando {}", this.entityClass.getSimpleName(), ex);
            throw new IllegalStateException("No se pudo guardar " + this.entityClass.getSimpleName(), ex);
        }
    }

    public Optional<T> findById(ID id) {
        try {
            return Optional.ofNullable(this.currentSession().find(this.entityClass, id));
        } catch (RuntimeException ex) {
            log.error("Error buscando {} por id {}", this.entityClass.getSimpleName(), id, ex);
            throw new IllegalStateException("No se pudo buscar " + this.entityClass.getSimpleName() + " por id", ex);
        }
    }

    public List<T> findAll() {
        try {
            return this.currentSession()
                    .createQuery("from " + this.entityClass.getSimpleName(), this.entityClass)
                    .getResultList();
        } catch (RuntimeException ex) {
            log.error("Error listando {}", this.entityClass.getSimpleName(), ex);
            throw new IllegalStateException("No se pudo listar " + this.entityClass.getSimpleName(), ex);
        }
    }

    public void delete(T entity) {
        try {
            this.currentSession().remove(this.currentSession().contains(entity) ? entity : this.currentSession().merge(entity));
        } catch (RuntimeException ex) {
            log.error("Error eliminando {}", this.entityClass.getSimpleName(), ex);
            throw new IllegalStateException("No se pudo eliminar " + this.entityClass.getSimpleName(), ex);
        }
    }
}

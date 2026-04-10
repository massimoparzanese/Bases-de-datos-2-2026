package unlp.info.bd2.repositories.Impl;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import unlp.info.bd2.model.User;
import unlp.info.bd2.repositories.UserRepository;

@Repository
public class UsersRepositoryImpl implements UserRepository {

    private static final Logger log = LoggerFactory.getLogger(UsersRepositoryImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    private Session currentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    public User save(User entity) {
        try {
            this.currentSession().merge(entity);
            return entity;
        } catch (RuntimeException ex) {
            log.error("Error guardando User", ex);
            throw new IllegalStateException("No se pudo guardar User", ex);
        }
    }

   
    public Optional<User> findById(Long id) {
        try {
            User user = this.currentSession().find(User.class, id);
            return Optional.ofNullable(user);
        } catch (RuntimeException ex) {
            log.error("Error buscando User por id {}", id, ex);
            throw new IllegalStateException("No se pudo buscar User por id", ex);
        }
    }

    
    public List<User> findAll() {
        try {
            return this.currentSession()
                    .createQuery("from User", User.class)
                    .getResultList();
        } catch (RuntimeException ex) {
            log.error("Error listando User", ex);
            throw new IllegalStateException("No se pudo listar User", ex);
        }
    }


    public void delete(User entity) {
        try {
            this.currentSession().remove(this.currentSession().contains(entity) ? entity : this.currentSession().merge(entity));
        } catch (RuntimeException ex) {
            log.error("Error eliminando User", ex);
            throw new IllegalStateException("No se pudo eliminar User", ex);
        }
    }
}

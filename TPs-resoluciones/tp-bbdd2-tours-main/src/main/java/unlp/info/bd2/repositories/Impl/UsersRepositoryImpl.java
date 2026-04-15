package unlp.info.bd2.repositories.Impl;

import org.hibernate.FlushMode;
import org.springframework.stereotype.Repository;

import unlp.info.bd2.model.User;
import unlp.info.bd2.repositories.UserRepository;
import java.util.List;
@Repository
public class UsersRepositoryImpl extends AbstractHibernateRepository<User, Long> implements UserRepository {

    public UsersRepositoryImpl() {
        super(User.class);
    }

    public List<User> getUserSpendingMoreThan(float mount) {
        try {
            return this.currentSession()
                    .createQuery(
                            "select distinct u from User u join u.purchaseList p where p.totalPrice >= :mount",
                            User.class)
                    .setParameter("mount", mount)
                    .getResultList();
        } catch (RuntimeException ex) {
            log.error("Error buscando usuarios con gasto mayor a {}", mount, ex);
            throw new IllegalStateException("No se pudo buscar usuarios por gasto", ex);
        }
    }

    public String getUsernameByIdWithoutFlushing(Long userId) {
        try {
            return this.currentSession()
                    .createQuery("select u.username from User u where u.id = :id", String.class)
                    .setParameter("id", userId)
                    .setHibernateFlushMode(FlushMode.MANUAL)
                    .getSingleResult();
        } catch (RuntimeException ex) {
            log.error("Error obteniendo username original para userId={}", userId, ex);
            throw new IllegalStateException("No se pudo obtener username original", ex);
        }
    }
}

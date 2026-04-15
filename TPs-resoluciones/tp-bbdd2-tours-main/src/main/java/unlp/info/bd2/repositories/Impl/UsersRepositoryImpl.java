package unlp.info.bd2.repositories.Impl;

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
                    .createQuery("select u from User u where (select sum(p.totalPrice) from Purchase p where p.user = u) > :mount", User.class)
                    .setParameter("mount", mount)
                    .getResultList();
        } catch (RuntimeException ex) {
            log.error("Error buscando usuarios con gasto mayor a {}", mount, ex);
            throw new IllegalStateException("No se pudo buscar usuarios por gasto", ex);
        }
    }
}

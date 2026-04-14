package unlp.info.bd2.repositories.Impl;

import org.springframework.stereotype.Repository;

import unlp.info.bd2.model.User;
import unlp.info.bd2.repositories.UserRepository;

@Repository
public class UsersRepositoryImpl extends AbstractHibernateRepository<User, Long> implements UserRepository {

    public UsersRepositoryImpl() {
        super(User.class);
    }
}

package unlp.info.bd2.repositories.Impl;

import org.springframework.stereotype.Repository;

import unlp.info.bd2.model.DriverUser;
import unlp.info.bd2.repositories.DriverUserRepository;

@Repository
public class DriversRepositoryImpl extends AbstractHibernateRepository<DriverUser, Long> implements DriverUserRepository {

    public DriversRepositoryImpl() {
        super(DriverUser.class);
    }
}

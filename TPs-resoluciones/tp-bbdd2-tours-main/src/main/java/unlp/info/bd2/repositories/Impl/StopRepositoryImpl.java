package unlp.info.bd2.repositories.Impl;

import org.springframework.stereotype.Repository;
import unlp.info.bd2.model.Stop;
import unlp.info.bd2.repositories.StopRepository;

@Repository
public class StopRepositoryImpl extends AbstractHibernateRepository<Stop, Long> implements StopRepository {

    public StopRepositoryImpl() {
        super(Stop.class);
    }
}

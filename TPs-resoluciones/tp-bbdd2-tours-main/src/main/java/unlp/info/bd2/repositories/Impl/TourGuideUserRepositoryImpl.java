package unlp.info.bd2.repositories.Impl;

import org.springframework.stereotype.Repository;

import unlp.info.bd2.model.TourGuideUser;
import unlp.info.bd2.repositories.TourGuideUserRepository;

@Repository
public class TourGuideUserRepositoryImpl extends AbstractHibernateRepository<TourGuideUser, Long>
        implements TourGuideUserRepository {

    public TourGuideUserRepositoryImpl() {
        super(TourGuideUser.class);
    }
}

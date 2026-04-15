package unlp.info.bd2.repositories.Impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import unlp.info.bd2.model.TourGuideUser;
import unlp.info.bd2.repositories.TourGuideUserRepository;

@Repository
public class TourGuideUserRepositoryImpl extends AbstractHibernateRepository<TourGuideUser, Long>
        implements TourGuideUserRepository {

    public TourGuideUserRepositoryImpl() {
        super(TourGuideUser.class);
    }

    @Override
    public List<TourGuideUser> getTourGuidesWithRating1() {
        try {
            return this.currentSession()
                    .createQuery(
                            "select distinct tgu from TourGuideUser tgu " +
                            "join tgu.routes r " +
                            "join Purchase p on p.route = r " +
                            "join p.review rev " +
                            "where rev.rating = 1",
                            TourGuideUser.class)
                    .getResultList();
        } catch (RuntimeException ex) {
            log.error("Error obteniendo guías turísticos con rating 1", ex);
            throw new IllegalStateException("No se pudo obtener los guías turísticos con rating 1", ex);
        }
    }
}

package unlp.info.bd2.repositories.Impl;

import org.springframework.stereotype.Repository;

import unlp.info.bd2.model.Route;
import unlp.info.bd2.repositories.RouteRepository;

@Repository
public class RouteRepositoryImpl extends AbstractHibernateRepository<Route, Long> implements RouteRepository {

    public RouteRepositoryImpl() {
        super(Route.class);
    }
}

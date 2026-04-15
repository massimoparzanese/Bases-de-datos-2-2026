package unlp.info.bd2.repositories.Impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import unlp.info.bd2.model.Route;
import unlp.info.bd2.model.Stop;
import unlp.info.bd2.repositories.RouteRepository;

@Repository
public class RouteRepositoryImpl extends AbstractHibernateRepository<Route, Long> implements RouteRepository {

    public RouteRepositoryImpl() {
        super(Route.class);
    }

    @Override
    public List<Route> getRoutesWithStop(Stop stop) {
        try {
            return this.currentSession()
                    .createQuery("select r from Route r join r.stops s where s.id = :stopId", Route.class)
                    .setParameter("stopId", stop.getId())
                    .getResultList();
        } catch (RuntimeException ex) {
            log.error("Error obteniendo rutas con el stop id {}", stop.getId(), ex);
            throw new IllegalStateException("No se pudo obtener las rutas con el stop indicado", ex);
        }
    }

    @Override
    public int getMaxStopOfRoutes() {
        try {
            Integer result = this.currentSession()
                    .createNativeQuery(
                            "select coalesce(max(stop_count), 0) " +
                            "from (select count(s.id) as stop_count from routes r " +
                            "left join route_stop rs on r.id = rs.route_id group by r.id) sq",
                            Integer.class)
                    .getSingleResult();
            return result != null ? result : 0;
        } catch (RuntimeException ex) {
            log.error("Error obteniendo el máximo de paradas en rutas", ex);
            throw new IllegalStateException("No se pudo obtener el máximo de paradas en rutas", ex);
        }
    }

    @Override
    public List<Route> getRoutesNotSell() {
        try {
            return this.currentSession()
                    .createQuery(
                            "select r from Route r " +
                            "where not exists (select 1 from Purchase p where p.route = r)",
                            Route.class)
                    .getResultList();
        } catch (RuntimeException ex) {
            log.error("Error obteniendo rutas sin ventas", ex);
            throw new IllegalStateException("No se pudo obtener las rutas sin ventas", ex);
        }
    }

    @Override
    public List<Route> getTop3RoutesWithMaxRating() {
        try {
            return this.currentSession()
                    .createQuery(
                    "select p.route from Purchase p " +
                    "join p.review rev " +
                    "group by p.route " +
                    "order by avg(rev.rating) desc",
                            Route.class)
                    .setMaxResults(3)
                    .getResultList();
        } catch (RuntimeException ex) {
            log.error("Error obteniendo las 3 rutas con mayor rating", ex);
            throw new IllegalStateException("No se pudo obtener las 3 rutas con mayor rating", ex);
        }
    }
}

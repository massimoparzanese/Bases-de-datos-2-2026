package unlp.info.bd2.repositories;

import unlp.info.bd2.model.Route;
import unlp.info.bd2.model.Stop;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends CrudRepository<Route, Long> {

    List<Route> getRoutesWithStop(Stop stop);
    
    int getMaxStopOfRoutes();
    
    List<Route> getRoutesNotSell();
    
    List<Route> getTop3RoutesWithMaxRating();

    @Query(value = "select r.* from routes r " +
            "join purchases p on p.route_id = r.id " +
            "group by r.id, r.name, r.price, r.total_km, r.max_number_users " +
            "order by count(p.id) desc " +
            "limit 3", nativeQuery = true)
    List<Route> getTop3RoutesWithMostPurchases();

    List<Route> findByPriceLessThanOrderByNameAsc(float price);
}

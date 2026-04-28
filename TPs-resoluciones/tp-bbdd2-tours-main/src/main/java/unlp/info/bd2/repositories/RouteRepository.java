package unlp.info.bd2.repositories;

import unlp.info.bd2.model.Route;
import unlp.info.bd2.model.Stop;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends CrudRepository<Route, Long> {

    boolean existsByTourGuideList_Id(Long tourGuideUserId);

    List<Route> getRoutesWithStop(Stop stop);
    

    @Query("""
        select max(size(r.stops))
        from Route r
        """)
    int getMaxStopOfRoutes();
    
    List<Route> getRoutesNotSell();
    
    @Query("""
        select r
        from Route r
        left join r.purchases p
        group by r.id, r.name, r.price, r.totalKm, r.maxNumberUsers
        order by avg(p.rating) desc
        """)
    List<Route> getTop3RoutesWithMaxRating(Pageable pageable);

    @Query("""
        select r
        from Purchase p
        join p.route r
        group by r.id, r.name, r.price, r.totalKm, r.maxNumberUsers
        order by count(p.id) desc
        """)
    List<Route> getTop3RoutesWithMostPurchases(Pageable pageable);

    List<Route> findByPriceLessThanOrderByNameAsc(float price);
}

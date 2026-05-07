package unlp.info.bd2.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import unlp.info.bd2.dto.RouteSummaryDTO;
import unlp.info.bd2.model.Route;
import unlp.info.bd2.model.Stop;

@Repository
public interface RouteRepository extends CrudRepository<Route, Long> {

    boolean existsByTourGuideList_Id(Long tourGuideUserId);

    @Query("""
        select distinct r
        from Route r
        join r.stops s
        where s = :stop
        """)
    List<Route> getRoutesWithStop(@Param("stop") Stop stop);

    @Query("""
        select max(size(r.stops))
        from Route r
        """)
    int getMaxStopOfRoutes();

    @Query("""
        from Route r
        where not exists (
            from Purchase p
            where p.route = r
        )
        """)
    List<Route> getRoutesNotSell();

    @Query("""
        select r
        from Route r
        left join Purchase p on p.route = r
        left join p.review rev
        group by r.id
        order by avg(rev.rating) desc
        """)
    List<Route> getTop3RoutesWithMaxRating(Pageable pageable);

    @Query("""
        select r
        from Purchase p
        join p.route r
        group by r.id
        order by count(p.id) desc
        """)
    List<Route> getTop3RoutesWithMostPurchases(Pageable pageable);

    List<Route> findByPriceLessThanOrderByNameAsc(float price);

    @Query("""
        SELECT r.name, COUNT(p), AVG(p.totalPrice)
        FROM Purchase p JOIN p.route r
        GROUP BY r.id, r.name
    """)
    List<Object[]> getRouteSummariesRaw();


    }

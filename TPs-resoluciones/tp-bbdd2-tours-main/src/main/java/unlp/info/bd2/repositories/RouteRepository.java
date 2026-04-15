package unlp.info.bd2.repositories;

import unlp.info.bd2.model.Route;
import unlp.info.bd2.model.Stop;
import java.util.List;

public interface RouteRepository extends BaseRepository<Route, Long> {

    List<Route> getRoutesWithStop(Stop stop);
    
    int getMaxStopOfRoutes();
    
    List<Route> getRoutesNotSell();
    
    List<Route> getTop3RoutesWithMaxRating();
}

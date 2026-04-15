package unlp.info.bd2.repositories;

import unlp.info.bd2.model.Service;

public interface ServiceRepository extends BaseRepository<Service, Long> {
    
    Service getMostDemandedService();
}

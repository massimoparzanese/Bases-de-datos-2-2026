package unlp.info.bd2.repositories;

import unlp.info.bd2.model.Service;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends CrudRepository<Service, Long> {
    
    Service getMostDemandedService();
}

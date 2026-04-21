package unlp.info.bd2.repositories;

import unlp.info.bd2.model.Stop;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface StopRepository extends CrudRepository<Stop, Long> {
}

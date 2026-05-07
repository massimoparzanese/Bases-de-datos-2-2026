package unlp.info.bd2.repositories;

import unlp.info.bd2.model.Stop;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface StopRepository extends CrudRepository<Stop, Long> {

	List<Stop> findByNameStartingWith(String prefix);
}

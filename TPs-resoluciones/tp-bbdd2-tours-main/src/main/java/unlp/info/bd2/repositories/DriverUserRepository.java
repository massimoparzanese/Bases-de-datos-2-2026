package unlp.info.bd2.repositories;

import unlp.info.bd2.model.DriverUser;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface DriverUserRepository extends CrudRepository<DriverUser, Long> {
}

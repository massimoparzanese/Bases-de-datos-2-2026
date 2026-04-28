package unlp.info.bd2.repositories;

import unlp.info.bd2.model.Purchase;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends CrudRepository<Purchase, Long> {
	@Override
	List<Purchase> findAll();

	Optional<Purchase> findByCode(String code);

	long countByRouteId(Long routeId);

	List<Purchase> findByUserId(Long userId);

	int getCountOfPurchasesBetweenDates(Date start,Date end);

	List<Purchase> findByUserUsername(String username);

	boolean existsByRouteId(Long routeId);
}

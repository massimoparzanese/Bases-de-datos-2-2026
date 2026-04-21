package unlp.info.bd2.repositories;

import unlp.info.bd2.model.Purchase;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.RouteMatcher.Route;

import java.util.Date;

@Repository
public interface PurchaseRepository extends CrudRepository<Purchase, Long> {
	List<Purchase> findAllByUsername(String username);

	int getCountOfPurchasesBetweenDates (Date start, Date end);

	List<Purchase> findByUserUsername(String username);

	boolean existsByRoute(Route route);
}

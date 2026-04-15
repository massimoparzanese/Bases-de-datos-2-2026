package unlp.info.bd2.repositories;

import unlp.info.bd2.model.Purchase;

import java.util.List;
import java.util.Date;

public interface PurchaseRepository extends BaseRepository<Purchase, Long> {
	List<Purchase> findAllByUsername(String username);

	int getCountOfPurchasesBetweenDates (Date start, Date end);
}

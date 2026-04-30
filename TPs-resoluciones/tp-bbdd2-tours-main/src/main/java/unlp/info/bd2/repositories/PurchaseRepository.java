package unlp.info.bd2.repositories;

import unlp.info.bd2.model.Purchase;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends CrudRepository<Purchase, Long> {
	@Override
	List<Purchase> findAll();

	Optional<Purchase> findByCode(String code);

	long countByRouteId(Long routeId);

	List<Purchase> findByUserId(Long userId);

	@Query("""
		select count(p)
		from Purchase p
		where p.date between :start and :end
		""")
	int getCountOfPurchasesBetweenDates(@Param("start") Date start, @Param("end") Date end);

	List<Purchase> findByUserUsername(String username);

	boolean existsByRouteId(Long routeId);
}

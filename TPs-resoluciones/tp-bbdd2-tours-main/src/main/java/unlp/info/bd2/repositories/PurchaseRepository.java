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
        where p.date between :from and :to
        """)
    int getCountOfPurchasesBetweenDates(@Param("from") Date from, @Param("to") Date to);

    @Query("""
        from Purchase p
        where p.user.username = :username
        """)
    List<Purchase> getAllPurchasesOfUsername(@Param("username") String username);

	boolean existsByRouteId(Long routeId);
}

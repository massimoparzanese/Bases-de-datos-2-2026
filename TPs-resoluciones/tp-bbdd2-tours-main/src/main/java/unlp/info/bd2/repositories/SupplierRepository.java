package unlp.info.bd2.repositories;

import unlp.info.bd2.model.Supplier;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends CrudRepository<Supplier, Long> {
    
    @Query("""
        select s
        from Supplier s
        join s.services serv
        join serv.itemServiceList i
        join i.purchase p
        group by s.id
        order by count(distinct p.id) desc
        """)
    List<Supplier> getTopNSuppliersInPurchases(Pageable pageable);

    Optional<Supplier> findByAuthorizationNumber(String authorizationNumber);

}
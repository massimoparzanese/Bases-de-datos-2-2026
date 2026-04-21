package unlp.info.bd2.repositories;

import unlp.info.bd2.model.Supplier;
import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends CrudRepository<Supplier, Long> {
    
    // Preguntar por lo del N, si conviene usar pageable o un int como parámetro
     @Query(""" 
    select s
    from Supplier s
    join s.routes r
    join r.purchases p
    group by s.id, s.name, s.email
    order by count(p.id) desc
    """)
    List<Supplier> getTopNSuppliersInPurchases(Pageable pageable);

}
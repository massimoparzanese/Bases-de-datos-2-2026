package unlp.info.bd2.repositories;

import unlp.info.bd2.model.Service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends CrudRepository<Service, Long> {

    List<Service> findBySupplierId(Long supplierId);

    List<Service> findByNameAndSupplierId(String name, Long supplierId);

    
    @Query("""
        select s
        from Service s
        left join s.itemServiceList i
        group by s.id, s.name, s.description
        order by coalesce(sum(i.quantity), 0) desc
        """)
    List<Service> getMostDemandedService(Pageable pageable);
}

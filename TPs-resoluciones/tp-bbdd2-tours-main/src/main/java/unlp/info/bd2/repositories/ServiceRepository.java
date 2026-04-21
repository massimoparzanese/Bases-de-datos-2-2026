package unlp.info.bd2.repositories;

import unlp.info.bd2.model.Service;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends CrudRepository<Service, Long> {

    
    @Query("""
        select s
        from Service s
        join s.routes r
        join r.purchases p
        group by s.id, s.name, s.description
        order by count(p.id) desc
        """)
    List<Service> getMostDemandedService(Pageable pageable);
}

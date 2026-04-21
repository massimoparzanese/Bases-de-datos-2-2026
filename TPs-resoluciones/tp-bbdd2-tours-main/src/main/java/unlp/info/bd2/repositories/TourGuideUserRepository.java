package unlp.info.bd2.repositories;

import unlp.info.bd2.model.TourGuideUser;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourGuideUserRepository extends CrudRepository<TourGuideUser, Long> {
    
   @Query("""
    select t
    from TourGuideUser t
    join t.routes r
    join r.purchases p
    join p.review rev
    group by t.id, t.name, t.email, t.education
    having avg(rev.rating) = 1
    """)
    List<TourGuideUser> getTourGuidesWithRating1();
}

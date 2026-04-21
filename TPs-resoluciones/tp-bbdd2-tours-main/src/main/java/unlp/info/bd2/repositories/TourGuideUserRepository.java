package unlp.info.bd2.repositories;

import unlp.info.bd2.model.TourGuideUser;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourGuideUserRepository extends CrudRepository<TourGuideUser, Long> {
    
    List<TourGuideUser> getTourGuidesWithRating1();
}

package unlp.info.bd2.repositories;

import unlp.info.bd2.model.TourGuideUser;
import java.util.List;

public interface TourGuideUserRepository extends BaseRepository<TourGuideUser, Long> {
    
    List<TourGuideUser> getTourGuidesWithRating1();
}

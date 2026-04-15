package unlp.info.bd2.repositories;

import unlp.info.bd2.model.User;
import java.util.List;
public interface UserRepository extends BaseRepository<User, Long> {

    List<User> getUserSpendingMoreThan(float mount);
}

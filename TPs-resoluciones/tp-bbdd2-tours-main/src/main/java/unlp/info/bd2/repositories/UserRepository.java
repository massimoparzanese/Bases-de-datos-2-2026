package unlp.info.bd2.repositories;

import unlp.info.bd2.model.User;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query("""
        select u
        from User u
        join u.purchaseList p
        group by u.id, u.name, u.email
        having sum(p.totalPrice) > :mount
        """)
    List<User> getUserSpendingMoreThan(float mount);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}

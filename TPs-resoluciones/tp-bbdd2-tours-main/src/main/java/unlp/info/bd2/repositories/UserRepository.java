package unlp.info.bd2.repositories;

import unlp.info.bd2.model.User;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.repository.query.Param;
import unlp.info.bd2.model.TourGuideUser;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query("""
        SELECT DISTINCT g
        FROM Purchase p
        JOIN p.route r
        JOIN r.tourGuideList g
        JOIN p.review rev
        WHERE rev.rating = 1 and TYPE(g) = TourGuideUser
    """)
    List<TourGuideUser> getTourGuidesWithRating1();

    @Query("""
        SELECT DISTINCT p.user FROM Purchase p
        WHERE p.totalPrice >= :amount
    """)
    List<User> getUserSpendingMoreThan(@Param("amount") float amount);
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}
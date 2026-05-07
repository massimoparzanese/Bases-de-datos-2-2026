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
        select distinct tg
        from TourGuideUser tg
        join tg.routes r
        join Purchase p on p.route = r
        join p.review rev
        group by tg.id, tg.username, tg.password, tg.name, tg.email, tg.birthdate, tg.phoneNumber, tg.active, tg.education
        having avg(rev.rating) = 1
        """)
    List<TourGuideUser> getTourGuidesWithRating1();

    @Query("""
        select distinct u
        from User u
        where TYPE(u) = User
        and (
            select coalesce(sum(p.totalPrice), 0.0)
            from Purchase p
            where p.user = u
            and p.review is not null
        ) > :amount
        """)
    List<User> getUserSpendingMoreThan(@Param("amount") float amount);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}

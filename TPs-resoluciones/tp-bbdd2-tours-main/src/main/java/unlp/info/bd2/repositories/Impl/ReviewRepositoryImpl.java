package unlp.info.bd2.repositories.Impl;

import org.springframework.stereotype.Repository;

import unlp.info.bd2.model.Review;
import unlp.info.bd2.repositories.ReviewRepository;

@Repository
public class ReviewRepositoryImpl extends AbstractHibernateRepository<Review, Long> implements ReviewRepository {

    public ReviewRepositoryImpl() {
        super(Review.class);
    }
}

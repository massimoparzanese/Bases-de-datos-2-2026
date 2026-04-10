package unlp.info.bd2.repositories.Impl;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import unlp.info.bd2.model.Review;
import unlp.info.bd2.repositories.ReviewRepository;

@Repository
public class ReviewRepositoryImpl implements ReviewRepository {

    private static final Logger log = LoggerFactory.getLogger(ReviewRepositoryImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    private Session currentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public Review save(Review entity) {
        try {
            this.currentSession().merge(entity);
            return entity;
        } catch (RuntimeException ex) {
            log.error("Error guardando Review", ex);
            throw new IllegalStateException("No se pudo guardar Review", ex);
        }
    }

    @Override
    public Optional<Review> findById(Long id) {
        try {
            Review review = this.currentSession().find(Review.class, id);
            return Optional.ofNullable(review);
        } catch (RuntimeException ex) {
            log.error("Error buscando Review por id {}", id, ex);
            throw new IllegalStateException("No se pudo buscar Review por id", ex);
        }
    }

    @Override
    public List<Review> findAll() {
        try {
            return this.currentSession()
                    .createQuery("from Review", Review.class)
                    .getResultList();
        } catch (RuntimeException ex) {
            log.error("Error listando Review", ex);
            throw new IllegalStateException("No se pudo listar Review", ex);
        }
    }

    @Override
    public void delete(Review entity) {
        try {
            this.currentSession().remove(this.currentSession().contains(entity) ? entity : this.currentSession().merge(entity));
        } catch (RuntimeException ex) {
            log.error("Error eliminando Review", ex);
            throw new IllegalStateException("No se pudo eliminar Review", ex);
        }
    }
}

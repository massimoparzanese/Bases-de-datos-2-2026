package unlp.info.bd2.repositories.Impl;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import unlp.info.bd2.model.TourGuideUser;
import unlp.info.bd2.repositories.TourGuideUserRepository;

@Repository
public class ToursRepositoryImpl implements TourGuideUserRepository{

    private static final Logger log = LoggerFactory.getLogger(ToursRepositoryImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    private Session currentSession() {
        return this.sessionFactory.getCurrentSession();
    }



    public TourGuideUser save(TourGuideUser entity) {
        try {
            this.currentSession().merge(entity);
            return entity;
        } catch (RuntimeException ex) {
            log.error("Error guardando TourGuideUser", ex);
            throw new IllegalStateException("No se pudo guardar TourGuideUser", ex);
        }
    }


    public Optional<TourGuideUser> findById(Long id) {
        try {
            TourGuideUser user = this.currentSession().find(TourGuideUser.class, id);
            return Optional.ofNullable(user);
        } catch (RuntimeException ex) {
            log.error("Error buscando TourGuideUser por id {}", id, ex);
            throw new IllegalStateException("No se pudo buscar TourGuideUser por id", ex);
        }
    }


    public List<TourGuideUser> findAll() {
        try {
            return this.currentSession()
                    .createQuery("from TourGuideUser", TourGuideUser.class)
                    .getResultList();
        } catch (RuntimeException ex) {
            log.error("Error listando TourGuideUser", ex);
            throw new IllegalStateException("No se pudo listar TourGuideUser", ex);
        }
    }


    public void delete(TourGuideUser entity) {
        try {
            this.currentSession().remove(this.currentSession().contains(entity) ? entity : this.currentSession().merge(entity));
        } catch (RuntimeException ex) {
            log.error("Error eliminando TourGuideUser", ex);
            throw new IllegalStateException("No se pudo eliminar TourGuideUser", ex);
        }
    }
    
}

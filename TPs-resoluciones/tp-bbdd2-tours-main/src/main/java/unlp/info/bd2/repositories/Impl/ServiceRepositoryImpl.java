package unlp.info.bd2.repositories.Impl;

import org.springframework.stereotype.Repository;

import unlp.info.bd2.model.Service;
import unlp.info.bd2.repositories.ServiceRepository;

@Repository
public class ServiceRepositoryImpl extends AbstractHibernateRepository<Service, Long> implements ServiceRepository {

    public ServiceRepositoryImpl() {
        super(Service.class);
    }

    @Override
    public Service getMostDemandedService() {
        try {
            return this.currentSession()
                    .createQuery(
                            "select s from Service s " +
                            "left join s.itemServiceList ism " +
                            "group by s.id " +
                            "order by coalesce(sum(ism.quantity), 0) desc",
                            Service.class)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (RuntimeException ex) {
            log.error("Error obteniendo el servicio más demandado", ex);
            throw new IllegalStateException("No se pudo obtener el servicio más demandado", ex);
        }
    }
}

package unlp.info.bd2.repositories.Impl;

import org.springframework.stereotype.Repository;

import unlp.info.bd2.model.Service;
import unlp.info.bd2.repositories.ServiceRepository;

@Repository
public class ServiceRepositoryImpl extends AbstractHibernateRepository<Service, Long> implements ServiceRepository {

    public ServiceRepositoryImpl() {
        super(Service.class);
    }
}

package unlp.info.bd2.repositories.Impl;

import org.springframework.stereotype.Repository;
import unlp.info.bd2.model.ItemService;
import unlp.info.bd2.repositories.ItemServiceRepository;

@Repository
public class ItemServiceRepositoryImpl extends AbstractHibernateRepository<ItemService, Long> implements ItemServiceRepository {

    public ItemServiceRepositoryImpl() {
        super(ItemService.class);
    }
}

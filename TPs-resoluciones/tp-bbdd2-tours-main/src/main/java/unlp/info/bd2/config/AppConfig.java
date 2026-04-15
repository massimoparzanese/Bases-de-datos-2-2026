package unlp.info.bd2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import unlp.info.bd2.repositories.Impl.ItemServiceRepositoryImpl;
import unlp.info.bd2.repositories.Impl.PurchaseRepositoryImpl;
import unlp.info.bd2.repositories.Impl.ReviewRepositoryImpl;
import unlp.info.bd2.repositories.Impl.RouteRepositoryImpl;
import unlp.info.bd2.repositories.Impl.ServiceRepositoryImpl;
import unlp.info.bd2.repositories.Impl.StopRepositoryImpl;
import unlp.info.bd2.repositories.Impl.SupplierRepositoryImpl;
import unlp.info.bd2.repositories.Impl.TourGuideUserRepositoryImpl;
import unlp.info.bd2.repositories.Impl.UsersRepositoryImpl;
import unlp.info.bd2.services.PurchaseService;
import unlp.info.bd2.services.RouteService;
import unlp.info.bd2.services.ServiceService;
import unlp.info.bd2.services.StopService;
import unlp.info.bd2.services.SupplierService;
import unlp.info.bd2.services.ToursService;
import unlp.info.bd2.services.UserService;
import unlp.info.bd2.services.Impl.PurchaseServiceImpl;
import unlp.info.bd2.services.Impl.RouteServiceImpl;
import unlp.info.bd2.services.Impl.ServiceServiceImpl;
import unlp.info.bd2.services.Impl.StopServiceImpl;
import unlp.info.bd2.services.Impl.SupplierServiceImpl;
import unlp.info.bd2.services.Impl.ToursServiceImpl;
import unlp.info.bd2.services.Impl.UserServiceImpl;

@Configuration
@ComponentScan(basePackages = "unlp.info.bd2.repositories.Impl")
public class AppConfig {

    @Bean
    public UserService userService(UsersRepositoryImpl usersRepository,
            TourGuideUserRepositoryImpl tourGuideUserRepository,
            RouteRepositoryImpl routeRepository) {
        return new UserServiceImpl(usersRepository, tourGuideUserRepository, routeRepository);
    }

    @Bean
    public StopService stopService(StopRepositoryImpl stopRepository) {
        return new StopServiceImpl(stopRepository);
    }

    @Bean
    public RouteService routeService(RouteRepositoryImpl routeRepository,
            PurchaseRepositoryImpl purchaseRepository) {
        return new RouteServiceImpl(routeRepository, purchaseRepository);
    }

    @Bean
    public SupplierService supplierService(SupplierRepositoryImpl supplierRepository) {
        return new SupplierServiceImpl(supplierRepository);
    }

    @Bean
    public ServiceService serviceService(ServiceRepositoryImpl serviceRepository,
            SupplierRepositoryImpl supplierRepository) {
        return new ServiceServiceImpl(serviceRepository, supplierRepository);
    }

    @Bean
    public PurchaseService purchaseService(PurchaseRepositoryImpl purchaseRepository,
            RouteRepositoryImpl routeRepository,
            UsersRepositoryImpl usersRepository,
            ServiceRepositoryImpl serviceRepository,
            ItemServiceRepositoryImpl itemServiceRepository,
            ReviewRepositoryImpl reviewRepository) {
        return new PurchaseServiceImpl(purchaseRepository, routeRepository, usersRepository, serviceRepository,
                itemServiceRepository, reviewRepository);
    }

    @Bean
    @Primary
    public ToursService createService() {
        return new ToursServiceImpl();
    }

}

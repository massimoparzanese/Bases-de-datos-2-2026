package unlp.info.bd2.services.Impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import unlp.info.bd2.model.DriverUser;
import unlp.info.bd2.model.ItemService;
import unlp.info.bd2.model.Purchase;
import unlp.info.bd2.model.Review;
import unlp.info.bd2.model.Route;
import unlp.info.bd2.model.Service;
import unlp.info.bd2.model.Stop;
import unlp.info.bd2.model.Supplier;
import unlp.info.bd2.model.TourGuideUser;
import unlp.info.bd2.model.User;
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
import unlp.info.bd2.utils.ToursException;

public class ToursServiceImpl implements ToursService {

    @Autowired
    private UsersRepositoryImpl usersRepository;

    @Autowired
    private TourGuideUserRepositoryImpl tourGuideUserRepository;

    @Autowired
    private StopRepositoryImpl stopRepository;

    @Autowired
    private RouteRepositoryImpl routeRepository;

    @Autowired
    private PurchaseRepositoryImpl purchaseRepository;

    @Autowired
    private SupplierRepositoryImpl supplierRepository;

    @Autowired
    private ServiceRepositoryImpl serviceRepository;

    @Autowired
    private ItemServiceRepositoryImpl itemServiceRepository;

    @Autowired
    private ReviewRepositoryImpl reviewRepository;

    private UserService userService;
    private StopService stopService;
    private RouteService routeService;
    private SupplierService supplierService;
    private ServiceService serviceService;
    private PurchaseService purchaseService;

    public ToursServiceImpl(UserService userService,
            StopService stopService,
            RouteService routeService,
            SupplierService supplierService,
            ServiceService serviceService,
            PurchaseService purchaseService) {
        this.userService = userService;
        this.stopService = stopService;
        this.routeService = routeService;
        this.supplierService = supplierService;
        this.serviceService = serviceService;
        this.purchaseService = purchaseService;
    }

    public ToursServiceImpl() {
    }

    @PostConstruct
    public void initDelegates() {
        if (this.userService == null) {
            this.userService = new UserServiceImpl(usersRepository, tourGuideUserRepository);
        }
        if (this.stopService == null) {
            this.stopService = new StopServiceImpl(stopRepository);
        }
        if (this.routeService == null) {
            this.routeService = new RouteServiceImpl(routeRepository, purchaseRepository);
        }
        if (this.supplierService == null) {
            this.supplierService = new SupplierServiceImpl(supplierRepository);
        }
        if (this.serviceService == null) {
            this.serviceService = new ServiceServiceImpl(serviceRepository, supplierRepository);
        }
        if (this.purchaseService == null) {
            this.purchaseService = new PurchaseServiceImpl(purchaseRepository, routeRepository, usersRepository,
                    serviceRepository, itemServiceRepository, reviewRepository);
        }
    }

    @Override
    public User createUser(String username, String password, String fullName, String email, Date birthdate,
            String phoneNumber) throws ToursException {
        return this.userService.createUser(username, password, fullName, email, birthdate, phoneNumber);
    }

    @Override
    public DriverUser createDriverUser(String username, String password, String fullName, String email, Date birthdate,
            String phoneNumber, String expedient) throws ToursException {
        return this.userService.createDriverUser(username, password, fullName, email, birthdate, phoneNumber, expedient);
    }

    @Override
    public TourGuideUser createTourGuideUser(String username, String password, String fullName, String email,
            Date birthdate, String phoneNumber, String education) throws ToursException {
        return this.userService.createTourGuideUser(username, password, fullName, email, birthdate, phoneNumber, education);
    }

    @Override
    public Optional<User> getUserById(Long id) throws ToursException {
        return this.userService.getUserById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) throws ToursException {
        return this.userService.getUserByUsername(username);
    }

    @Override
    public User updateUser(User user) throws ToursException {
        return this.userService.updateUser(user);
    }

    @Override
    public void deleteUser(User user) throws ToursException {
        this.userService.deleteUser(user.getId());
    }

    @Override
    public Stop createStop(String name, String description) throws ToursException {
        return this.stopService.createStop(name, description);
    }

    @Override
    public List<Stop> getStopByNameStart(String name) {
        try {
            return this.stopService.getStopsByNameStart(name);
        } catch (ToursException ex) {
            throw new IllegalStateException("No se pudo buscar paradas por nombre", ex);
        }
    }

    @Override
    public Route createRoute(String name, float price, float totalKm, int maxNumberOfUsers, List<Stop> stops)
            throws ToursException {
        return this.routeService.createRoute(name, price, totalKm, maxNumberOfUsers, stops);
    }

    @Override
    public Optional<Route> getRouteById(Long id) {
        try {
            return this.routeService.getRouteById(id);
        } catch (ToursException ex) {
            throw new IllegalStateException("No se pudo buscar la ruta por id", ex);
        }
    }

    @Override
    public List<Route> getRoutesBelowPrice(float price) {
        try {
            return this.routeService.getAllRoutes().stream()
                    .filter(r -> r.getPrice() < price)
                    .toList();
        } catch (ToursException ex) {
            throw new IllegalStateException("No se pudo obtener rutas por precio", ex);
        }
    }

    @Override
    public void assignDriverByUsername(String username, Long idRoute) throws ToursException {
        Route route = this.routeService.getRouteById(idRoute)
                .orElseThrow(() -> new ToursException("No pudo realizarse la asignación"));
        User user = this.userService.getUserByUsername(username)
                .orElseThrow(() -> new ToursException("No pudo realizarse la asignación"));
        if (!(user instanceof DriverUser)) {
            throw new ToursException("No pudo realizarse la asignación");
        }
        route.addDriver((DriverUser) user);
        this.routeService.updateRoute(route);
    }

    @Override
    public void assignTourGuideByUsername(String username, Long idRoute) throws ToursException {
        Route route = this.routeService.getRouteById(idRoute)
                .orElseThrow(() -> new ToursException("No pudo realizarse la asignación"));
        User user = this.userService.getUserByUsername(username)
                .orElseThrow(() -> new ToursException("No pudo realizarse la asignación"));
        if (!(user instanceof TourGuideUser)) {
            throw new ToursException("No pudo realizarse la asignación");
        }
        route.addTourGuide((TourGuideUser) user);
        this.routeService.updateRoute(route);
    }

    @Override
    public Supplier createSupplier(String businessName, String authorizationNumber) throws ToursException {
        return this.supplierService.createSupplier(businessName, authorizationNumber);
    }

    @Override
    public Service addServiceToSupplier(String name, float price, String description, Supplier supplier)
            throws ToursException {
        return this.serviceService.createService(name, price, description, supplier);
    }

    @Override
    public Service updateServicePriceById(Long id, float newPrice) throws ToursException {
        return this.serviceService.updateServicePrice(id, newPrice);
    }

    @Override
    public Optional<Supplier> getSupplierById(Long id) {
        try {
            return this.supplierService.getSupplierById(id);
        } catch (ToursException ex) {
            throw new IllegalStateException("No se pudo buscar proveedor por id", ex);
        }
    }

    @Override
    public Optional<Supplier> getSupplierByAuthorizationNumber(String authorizationNumber) {
        try {
            return this.supplierService.getSupplierByAuthorizationNumber(authorizationNumber);
        } catch (ToursException ex) {
            throw new IllegalStateException("No se pudo buscar proveedor por autorizacion", ex);
        }
    }

    @Override
    public Optional<Service> getServiceByNameAndSupplierId(String name, Long id) throws ToursException {
        return this.serviceService.getAllServices().stream()
                .filter(s -> s.getName() != null && s.getName().equals(name))
                .filter(s -> s.getSupplier() != null && s.getSupplier().getId() != null && s.getSupplier().getId().equals(id))
                .findFirst();
    }

    @Override
    public Purchase createPurchase(String code, Route route, User user) throws ToursException {
        return this.purchaseService.createPurchase(code, route.getId(), user.getId());
    }

    @Override
    public Purchase createPurchase(String code, Date date, Route route, User user) throws ToursException {
        return this.purchaseService.createPurchase(code, date, route.getId(), user.getId());
    }

    @Override
    public ItemService addItemToPurchase(Service service, int quantity, Purchase purchase) throws ToursException {
        return this.purchaseService.addItemToPurchase(purchase.getId(), service.getId(), quantity);
    }

    @Override
    public Optional<Purchase> getPurchaseByCode(String code) {
        try {
            return this.purchaseService.getPurchaseByCode(code);
        } catch (ToursException ex) {
            throw new IllegalStateException("No se pudo buscar compra por codigo", ex);
        }
    }

    @Override
    public void deletePurchase(Purchase purchase) throws ToursException {
        this.purchaseService.deletePurchase(purchase.getId());
    }

    @Override
    public Review addReviewToPurchase(int rating, String comment, Purchase purchase) throws ToursException {
        return this.purchaseService.addReviewToPurchase(purchase.getId(), rating, comment);
    }

    @Override
    public void deleteRoute(Route route) throws ToursException {
        this.routeService.deleteRouteIfNoSales(route.getId());
    }

    @Override
    public List<Purchase> getAllPurchasesOfUsername(String username) {
        try {
            return this.purchaseService.getAllPurchasesOfUsername(username);
        } catch (ToursException ex) {
            throw new IllegalStateException("No se pudo buscar compras del usuario", ex);
        }
    }

    @Override
    public List<User> getUserSpendingMoreThan(float mount) {
        try {
            return this.userService.getUserSpendingMoreThan(mount);
        } catch (ToursException ex) {
            throw new IllegalStateException("No se pudo obtener usuarios por gasto", ex);
        }
    }

    @Override
    public List<Supplier> getTopNSuppliersInPurchases(int n) {
        try {
            return this.supplierService.getTopNSuppliersInPurchases(n);
        } catch (ToursException ex) {
            throw new IllegalStateException("No se pudo obtener proveedores top", ex);
        }
    }

    @Override
    public long getCountOfPurchasesBetweenDates(Date start, Date end) {
        try {
            return this.purchaseService.getCountOfPurchasesBetweenDates(start, end);
        } catch (ToursException ex) {
            throw new IllegalStateException("No se pudo obtener conteo entre fechas", ex);
        }
    }

    @Override
    public List<Route> getRoutesWithStop(Stop stop) {
        try {
            return this.routeService.getRoutesWithStop(stop);
        } catch (ToursException ex) {
            throw new IllegalStateException("No se pudo buscar rutas por stop", ex);
        }
    }

    @Override
    public Long getMaxStopOfRoutes() {
        try {
            return Long.valueOf(this.routeService.getMaxStopOfRoutes());
        } catch (ToursException ex) {
            throw new IllegalStateException("No se pudo obtener maximo de stops", ex);
        }
    }

    @Override
    public List<Route> getRoutsNotSell() {
        try {
            return this.routeService.getRoutesNotSell();
        } catch (ToursException ex) {
            throw new IllegalStateException("No se pudo obtener rutas no vendidas", ex);
        }
    }

    @Override
    public List<Route> getTop3RoutesWithMaxRating() {
        try {
            return this.routeService.getTop3RoutesWithMaxRating();
        } catch (ToursException ex) {
            throw new IllegalStateException("No se pudo obtener top 3 rutas con rating", ex);
        }
    }

    @Override
    public Service getMostDemandedService() {
        try {
            return this.serviceService.getMostDemandedService();
        } catch (ToursException ex) {
            throw new IllegalStateException("No se pudo obtener servicio mas demandado", ex);
        }
    }

    @Override
    public List<TourGuideUser> getTourGuidesWithRating1() {
        try {
            return this.userService.getTourGuidesWithRating1();
        } catch (ToursException ex) {
            throw new IllegalStateException("No se pudo obtener guias con rating 1", ex);
        }
    }
    
}

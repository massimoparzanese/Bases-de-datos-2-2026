package unlp.info.bd2.services.Impl;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import unlp.info.bd2.repositories.PurchaseRepository;
import unlp.info.bd2.repositories.ItemServiceRepository;
import unlp.info.bd2.repositories.RouteRepository;
import unlp.info.bd2.repositories.ServiceRepository;
import unlp.info.bd2.repositories.ReviewRepository;
import unlp.info.bd2.repositories.StopRepository;
import unlp.info.bd2.repositories.SupplierRepository;
import unlp.info.bd2.repositories.TourGuideUserRepository;
import unlp.info.bd2.repositories.UserRepository;
import unlp.info.bd2.services.ToursService;
import unlp.info.bd2.services.PurchaseService;
import unlp.info.bd2.services.RouteService;
import unlp.info.bd2.services.ServiceService;
import unlp.info.bd2.services.StopService;
import unlp.info.bd2.services.SupplierService;
import unlp.info.bd2.services.UserService;
import unlp.info.bd2.utils.ToursException;

public class ToursServiceImpl implements ToursService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TourGuideUserRepository tourGuideUserRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ItemServiceRepository itemServiceRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private StopRepository stopRepository;

    private UserService userService;
    private RouteService routeService;
    private SupplierService supplierService;
    private ServiceService serviceService;
    private PurchaseService purchaseService;
    private StopService stopService;

    @PostConstruct
    void initServices() {
        this.userService = new UserServiceImpl(userRepository, tourGuideUserRepository, routeRepository);
        this.routeService = new RouteServiceImpl(routeRepository, purchaseRepository, userRepository);
        this.supplierService = new SupplierServiceImpl(supplierRepository);
        this.serviceService = new ServiceServiceImpl(serviceRepository, supplierRepository);
        this.purchaseService = new PurchaseServiceImpl(purchaseRepository, routeRepository, userRepository, serviceRepository, itemServiceRepository, reviewRepository);
        this.stopService = new StopServiceImpl(stopRepository);
    }

    public ToursServiceImpl() {
    }

    @Override
    @Transactional(rollbackFor = ToursException.class)
    public User createUser(String username, String password, String fullName, String email, Date birthdate,
            String phoneNumber) throws ToursException {
        return userService.createUser(username, password, fullName, email, birthdate, phoneNumber);
    }

    @Override
    @Transactional(rollbackFor = ToursException.class)
    public DriverUser createDriverUser(String username, String password, String fullName, String email, Date birthdate,
            String phoneNumber, String expedient) throws ToursException {
        return userService.createDriverUser(username, password, fullName, email, birthdate, phoneNumber, expedient);
    }

    @Override
    @Transactional(rollbackFor = ToursException.class)
    public TourGuideUser createTourGuideUser(String username, String password, String fullName, String email,
            Date birthdate, String phoneNumber, String education) throws ToursException {
        return userService.createTourGuideUser(username, password, fullName, email, birthdate, phoneNumber, education);
    }

    @Override
    public Optional<User> getUserById(Long id) throws ToursException {
        return userService.getUserById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) throws ToursException {
        return userService.getUserByUsername(username);
    }

    @Override
    @Transactional(rollbackFor = ToursException.class)
    public User updateUser(User user) throws ToursException {
        return userService.updateUser(user);
    }

    @Override
    @Transactional(rollbackFor = ToursException.class)
    public void deleteUser(User user) throws ToursException {
        userService.deleteUser(user.getId());
    }

    @Override
    @Transactional(rollbackFor = ToursException.class)
    public Stop createStop(String name, String description) throws ToursException {
        return stopService.createStop(name, description);
    }

    @Override
    public List<Stop> getStopByNameStart(String name) {
        try {
            return stopService.getStopsByNameStart(name);
        } catch (ToursException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    @Transactional(rollbackFor = ToursException.class)
    public Route createRoute(String name, float price, float totalKm, int maxNumberOfUsers, List<Stop> stops)
            throws ToursException {
        return routeService.createRoute(name, price, totalKm, maxNumberOfUsers, stops);
    }

    @Override
    public Optional<Route> getRouteById(Long id) {
        try {
            return routeService.getRouteById(id);
        } catch (ToursException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Route> getRoutesBelowPrice(float price) {
        try {
            return routeService.getRoutesBelowPrice(price);
        } catch (ToursException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    @Transactional(rollbackFor = ToursException.class)
    public void assignDriverByUsername(String username, Long idRoute) throws ToursException {
        routeService.assignDriverByUsername(username, idRoute);
    }

    @Override
    @Transactional(rollbackFor = ToursException.class)
    public void assignTourGuideByUsername(String username, Long idRoute) throws ToursException {
        routeService.assignTourGuideByUsername(username, idRoute);
    }

    @Override
    @Transactional(rollbackFor = ToursException.class)
    public Supplier createSupplier(String businessName, String authorizationNumber) throws ToursException {
        return supplierService.createSupplier(businessName, authorizationNumber);
    }

    @Override
    @Transactional(rollbackFor = ToursException.class)
    public Service addServiceToSupplier(String name, float price, String description, Supplier supplier)
            throws ToursException {
        return serviceService.createService(name, price, description, supplier);
    }

    @Override
    @Transactional(rollbackFor = ToursException.class)
    public Service updateServicePriceById(Long id, float newPrice) throws ToursException {
        return serviceService.updateServicePrice(id, newPrice);
    }

    @Override
    public Optional<Supplier> getSupplierById(Long id) {
        try {
            return supplierService.getSupplierById(id);
        } catch (ToursException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Optional<Supplier> getSupplierByAuthorizationNumber(String authorizationNumber) {
        try {
            return supplierService.getSupplierByAuthorizationNumber(authorizationNumber);
        } catch (ToursException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Optional<Service> getServiceByNameAndSupplierId(String name, Long id) throws ToursException {
        return serviceService.getServiceByNameAndSupplierId(name, id);
    }

    @Override
    @Transactional(rollbackFor = ToursException.class)
    public Purchase createPurchase(String code, Route route, User user) throws ToursException {
        return purchaseService.createPurchase(code, route.getId(), user.getId());
    }

    @Override
    @Transactional(rollbackFor = ToursException.class)
    public Purchase createPurchase(String code, Date date, Route route, User user) throws ToursException {
        return purchaseService.createPurchase(code, date, route.getId(), user.getId());
    }

    @Override
    @Transactional(rollbackFor = ToursException.class)
    public ItemService addItemToPurchase(Service service, int quantity, Purchase purchase) throws ToursException {
        return purchaseService.addItemToPurchase(purchase.getId(), service.getId(), quantity);
    }

    @Override
    public Optional<Purchase> getPurchaseByCode(String code) {
        try {
            return purchaseService.getPurchaseByCode(code);
        } catch (ToursException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    @Transactional(rollbackFor = ToursException.class)
    public void deletePurchase(Purchase purchase) throws ToursException {
        purchaseService.deletePurchase(purchase.getId());
    }

    @Override
    @Transactional(rollbackFor = ToursException.class)
    public Review addReviewToPurchase(int rating, String comment, Purchase purchase) throws ToursException {
        return purchaseService.addReviewToPurchase(purchase.getId(), rating, comment);
    }

    @Override
    @Transactional(rollbackFor = ToursException.class)
    public void deleteRoute(Route route) throws ToursException {
        routeService.deleteRouteIfNoSales(route.getId());
    }

    @Override
    public List<Purchase> getAllPurchasesOfUsername(String username) {
        try {
            return purchaseService.getAllPurchasesOfUsername(username);
        } catch (ToursException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<User> getUserSpendingMoreThan(float mount) {
        try {
            return userService.getUserSpendingMoreThan(mount);
        } catch (ToursException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Supplier> getTopNSuppliersInPurchases(int n) {
        try {
            return supplierService.getTopNSuppliersInPurchases(n);
        } catch (ToursException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public long getCountOfPurchasesBetweenDates(Date start, Date end) {
        try {
            return purchaseService.getCountOfPurchasesBetweenDates(start, end);
        } catch (ToursException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Route> getRoutesWithStop(Stop stop) {
        try {
            return routeService.getRoutesWithStop(stop);
        } catch (ToursException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Long getMaxStopOfRoutes() {
        try {
            return Long.valueOf(routeService.getMaxStopOfRoutes());
        } catch (ToursException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Route> getRoutesNotSell() {
        try {
            return routeService.getRoutesNotSell();
        } catch (ToursException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Route> getTop3RoutesWithMaxRating() {
        try {
            return routeService.getTop3RoutesWithMaxRating();
        } catch (ToursException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Service getMostDemandedService() {
        try {
            return serviceService.getMostDemandedService();
        } catch (ToursException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<TourGuideUser> getTourGuidesWithRating1() {
        try {
            return userService.getTourGuidesWithRating1();
        } catch (ToursException ex) {
            throw new RuntimeException(ex);
        }
    }
}

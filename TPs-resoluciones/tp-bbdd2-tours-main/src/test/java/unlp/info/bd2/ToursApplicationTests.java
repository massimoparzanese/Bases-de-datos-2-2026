package unlp.info.bd2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import unlp.info.bd2.config.AppConfig;
import unlp.info.bd2.config.HibernateConfiguration;
import unlp.info.bd2.model.*;
import unlp.info.bd2.services.PurchaseService;
import unlp.info.bd2.services.RouteService;
import unlp.info.bd2.services.ServiceService;
import unlp.info.bd2.services.StopService;
import unlp.info.bd2.services.SupplierService;
import unlp.info.bd2.services.UserService;
import unlp.info.bd2.utils.ToursException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ContextConfiguration(classes = {HibernateConfiguration.class, AppConfig.class}, loader = AnnotationConfigContextLoader.class)
@ExtendWith(SpringExtension.class)
@Transactional
@Rollback(true)
class ToursApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private StopService stopService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private PurchaseService purchaseService;

    private Date dob1;
    private Date dob2;
    private Date dpri;
    private Date dyes;

    @BeforeEach
    public void setUp() {
        Calendar cal1 = Calendar.getInstance();
        cal1.set(1980, Calendar.APRIL, 5);
        this.dob1 = cal1.getTime();
        cal1.set(1992, Calendar.SEPTEMBER, 16);
        this.dob2 = cal1.getTime();
        cal1.set(2022, Calendar.SEPTEMBER, 21);
        this.dpri = cal1.getTime();
        cal1.set(2024, Calendar.MARCH, 20);
        this.dyes = cal1.getTime();
    }


    @Test
    void createAndGetUserTest() throws ToursException {
        User user1 = this.userService.createUser("user1", "1234", "Usuario Uno", "user1@gmail.com", dob1, "000111222333");
        assertNotNull(user1.getId());
        assertEquals("user1", user1.getUsername());
        assertEquals("Usuario Uno", user1.getName());
        assertEquals("user1@gmail.com", user1.getEmail());
        assertEquals(dob1, user1.getBirthdate());
        DriverUser driverUser1 = this.userService.createDriverUser("userD", "1234", "Usuario Driver", "userd@gmail.com", dob2, "000111222444", "exp...");
        assertNotNull(driverUser1.getId());
        TourGuideUser tourGuideUser1 = this.userService.createTourGuideUser("userG", "1234", "Usuario TourGuide", "userg@gmail.com", dob2, "000111222555", "edu...");
        assertNotNull(tourGuideUser1.getId());

        Optional<User> opUserFromDB = this.userService.getUserById(user1.getId());
        assertTrue(opUserFromDB.isPresent());
        User user = opUserFromDB.get();
        assertEquals(user1.getId(), user.getId());
        assertEquals("user1", user.getUsername());
        assertEquals("Usuario Uno", user.getName());
        assertEquals("user1@gmail.com", user.getEmail());
        assertTrue(user.getPurchaseList().isEmpty());

        Optional<User> opUserFromDB2 = this.userService.getUserByUsername("userD");
        assertTrue(opUserFromDB2.isPresent());
        DriverUser driverUser = (DriverUser) opUserFromDB2.get();
        assertEquals(driverUser.getId(), driverUser1.getId());
        assertEquals(driverUser.getExpedient(), "exp...");

        assertThrows(ToursException.class, () -> this.userService.createUser("userD", "1234", "Otro usuario", "otromail@gmail.com", dob1, "000111222999"), "Constraint Violation");
    }

    @Test
    void updateUserTest() throws ToursException {
        User user1 = this.userService.createUser("user1", "1234", "Usuario Uno", "user1@gmail.com", dob1, "000111222333");
        DriverUser driverUser = this.userService.createDriverUser("userD", "1234", "Usuario Driver", "userd@gmail.com", dob2, "000111222444", "exp...");

        assertEquals("000111222333", user1.getPhoneNumber());
        user1.setPhoneNumber("000000000000");
        user1 = this.userService.updateUser(user1);
        assertNotEquals("000111222333", user1.getPhoneNumber());
        assertEquals("000000000000", user1.getPhoneNumber());

        assertEquals("exp...", driverUser.getExpedient());
        driverUser.setExpedient("nuevo expediente");
        driverUser = (DriverUser) this.userService.updateUser(driverUser);
        assertNotEquals("exp...", driverUser.getExpedient());
        assertEquals("nuevo expediente", driverUser.getExpedient());

        user1.setUsername("user2");
        this.userService.updateUser(user1);
        Optional<User> opUserFromDB = this.userService.getUserByUsername("user2");
        assertTrue(opUserFromDB.isEmpty());
        Optional<User> opUnmodifiedUserFromDB = this.userService.getUserByUsername("user1");
        assertTrue(opUnmodifiedUserFromDB.isPresent());
        User unmodifiedUserFromDB = opUnmodifiedUserFromDB.get();
        assertEquals(unmodifiedUserFromDB.getId(), user1.getId());
    }

    @Test
    void createAndGetRoutesAndStopsTest() throws ToursException {
        Stop stop1 = this.stopService.createStop("Estadio Monumental", "Estadio de River Plate");
        assertNotNull(stop1.getId());
        Stop stop2 = this.stopService.createStop("Estadio La Bombonera", "Estadio de Boca Junions");
        Stop stop3 = this.stopService.createStop("Estadio Libertadores de America", "Estadio de Independiente");

        List<Stop> stopList1 = this.stopService.getStopsByNameStart("Estadio L");
        assertEquals(2, stopList1.size());
        List<Stop> stopList2 = this.stopService.getStopsByNameStart("Estadio");
        assertEquals(3, stopList2.size());
        List<Stop> stopList3 = this.stopService.getStopsByNameStart("Monumental");
        assertEquals(0, stopList3.size());

        List<Stop> stops1 = new ArrayList<Stop>(Arrays.asList(stop1, stop2, stop3));
        List<Stop> stops2 = new ArrayList<Stop>(Arrays.asList(stop3, stop2));
        Route route1 = this.routeService.createRoute("Estadios", 20000, 55.5f, 3, stops1);
        assertNotNull(route1.getId());

        Optional<Route> opRoute1 = this.routeService.getRouteById(route1.getId());
        assertTrue(opRoute1.isPresent());
        Route route3 = opRoute1.get();
        assertEquals("Estadios", route3.getName());
        assertEquals(3, route3.getStops().size());

        Route route2 = this.routeService.createRoute("Estadios 2", 15000, 30f, 2, stops2);
        List<Route> listRoutes1 = this.routeService.getAllRoutes().stream()
                .filter(route -> route.getPrice() < 50000f)
                .toList();
        assertEquals(2, listRoutes1.size());
        List<Route> listRoutes2 = this.routeService.getAllRoutes().stream()
                .filter(route -> route.getPrice() < 17000f)
                .toList();
        assertEquals(1, listRoutes2.size());
        Route routeFromList = listRoutes2.get(0);
        assertEquals("Estadios 2", routeFromList.getName());
    }

    @Test
    void assignWorkersToRoutesTest() throws ToursException {
        Stop stop1 = this.stopService.createStop("Estadio Monumental", "Estadio de River Plate");
        Stop stop2 = this.stopService.createStop("Estadio La Bombonera", "Estadio de Boca Junions");
        Stop stop3 = this.stopService.createStop("Estadio Libertadores de America", "Estadio de Independiente");
        List<Stop> stops1 = new ArrayList<Stop>(Arrays.asList(stop1, stop2, stop3));
        Route route1 = this.routeService.createRoute("Estadios", 20000, 55.5f, 3, stops1);
        DriverUser driverUser1 = this.userService.createDriverUser("userD1", "1234", "Usuario Driver", "userd1@gmail.com", dob2, "000111222444", "exp...");
        DriverUser driverUser2 = this.userService.createDriverUser("userD2", "1234", "Usuario Driver", "userd2@gmail.com", dob2, "000111222444", "exp...");
        TourGuideUser tourGuideUser1 = this.userService.createTourGuideUser("userG1", "1234", "Usuario TourGuide", "userg1@gmail.com", dob2, "000111222555", "edu...");

        Route routeManaged = this.routeService.getRouteById(route1.getId()).orElseThrow(() -> new ToursException("No pudo realizarse la asignación"));
        DriverUser driverManaged1 = (DriverUser) this.userService.getUserByUsername(driverUser1.getUsername()).orElseThrow(() -> new ToursException("No pudo realizarse la asignación"));
        DriverUser driverManaged2 = (DriverUser) this.userService.getUserByUsername(driverUser2.getUsername()).orElseThrow(() -> new ToursException("No pudo realizarse la asignación"));
        TourGuideUser guideManaged1 = (TourGuideUser) this.userService.getUserByUsername(tourGuideUser1.getUsername()).orElseThrow(() -> new ToursException("No pudo realizarse la asignación"));
        routeManaged.addDriver(driverManaged1);
        routeManaged.addDriver(driverManaged2);
        routeManaged.addTourGuide(guideManaged1);
        this.routeService.updateRoute(routeManaged);
        Optional<Route> optionalRoute = this.routeService.getRouteById(route1.getId());
        assertTrue(optionalRoute.isPresent());
        Route route = optionalRoute.get();
        assertEquals(2, route.getDriverList().size());
        assertEquals(1, route.getTourGuideList().size());
        assertEquals("userG1", route.getTourGuideList().get(0).getUsername());

        assertThrows(ToursException.class, () -> {
            Route badRoute = this.routeService.getRouteById(tourGuideUser1.getId()).orElseThrow(() -> new ToursException("No pudo realizarse la asignación"));
            TourGuideUser badGuide = (TourGuideUser) this.userService.getUserByUsername("user_no_existente").orElseThrow(() -> new ToursException("No pudo realizarse la asignación"));
            badRoute.addTourGuide(badGuide);
            this.routeService.updateRoute(badRoute);
        }, "No pudo realizarse la asignación");
        assertThrows(ToursException.class, () -> {
            Route badRoute = this.routeService.getRouteById(1000000L).orElseThrow(() -> new ToursException("No pudo realizarse la asignación"));
            DriverUser driver = (DriverUser) this.userService.getUserByUsername(driverUser1.getUsername()).orElseThrow(() -> new ToursException("No pudo realizarse la asignación"));
            badRoute.addDriver(driver);
            this.routeService.updateRoute(badRoute);
        }, "No pudo realizarse la asignación");
    }

    @Test
    void createAndGetSupplierAndService() throws ToursException {
        Supplier supplier1 = this.supplierService.createSupplier("Supplier1", "000111");
        assertNotNull(supplier1.getId());
        assertEquals("Supplier1", supplier1.getBusinessName());
        Service service1 = this.serviceService.createService("Servicio1", 500f, "primer servicio", supplier1);
        assertNotNull(service1.getId());
        assertEquals("Servicio1", service1.getName());
        assertEquals(supplier1.getId(), service1.getSupplier().getId());
        assertEquals(supplier1.getServices().get(0).getId(), service1.getId());

        Optional<Supplier> optionalSupplier1 = this.supplierService.getSupplierById(supplier1.getId());
        assertTrue(optionalSupplier1.isPresent());
        Supplier supplier2 = optionalSupplier1.get();
        assertEquals(supplier1.getId(), supplier2.getId());
        assertEquals("000111", supplier2.getAuthorizationNumber());
        Optional<Supplier> optionalSupplier2 = this.supplierService.getSupplierByAuthorizationNumber("000111");
        assertTrue(optionalSupplier2.isPresent());
        Supplier supplier3 = optionalSupplier2.get();
        assertEquals(supplier1.getId(), supplier3.getId());
        Optional<Supplier> optionalSupplier3 = this.supplierService.getSupplierByAuthorizationNumber("001111");
        assertFalse(optionalSupplier3.isPresent());

        Optional<Service> optionalService1 = this.serviceService.getAllServices().stream()
                .filter(service -> service.getName() != null && service.getName().equals("Servicio1"))
                .filter(service -> service.getSupplier() != null
                        && service.getSupplier().getId() != null
                        && service.getSupplier().getId().equals(supplier1.getId()))
                .findFirst();
        assertTrue(optionalService1.isPresent());
        Service service2 = optionalService1.get();
        assertEquals(service1.getId(), service2.getId());
        assertEquals(service1.getDescription(), "primer servicio");
        Optional<Service> optionalService2 = this.serviceService.getAllServices().stream()
                .filter(service -> service.getName() != null && service.getName().equals("Servicio2"))
                .filter(service -> service.getSupplier() != null
                        && service.getSupplier().getId() != null
                        && service.getSupplier().getId().equals(supplier1.getId()))
                .findFirst();
        assertFalse(optionalService2.isPresent());

        assertThrows(ToursException.class, () -> this.supplierService.createSupplier("Supplier2", "000111"), "Constraint Violation");
    }

    @Test
    void updateServicePriceTest() throws ToursException {
        Supplier supplier1 = this.supplierService.createSupplier("Supplier1", "000111");
        Service service1 = this.serviceService.createService("Servicio1", 500f, "primer servicio", supplier1);
        assertEquals(500f, service1.getPrice());

        Service service2 = this.serviceService.updateServicePrice(service1.getId(), 600f);
        assertEquals(600f, service2.getPrice());

        assertThrows(ToursException.class, () -> this.serviceService.updateServicePrice(100000L, 500f), "No existe el producto");
    }

    @Test
    void createAndGetPurchaseTest() throws ToursException {
        User user1 = this.userService.createUser("user1", "1234", "Usuario Uno", "user1@gmail.com", dob1, "000111222333");
        Stop stop1 = this.stopService.createStop("Estadio Monumental", "Estadio de River Plate");
        Stop stop2 = this.stopService.createStop("Estadio La Bombonera", "Estadio de Boca Junions");
        Stop stop3 = this.stopService.createStop("Estadio Libertadores de America", "Estadio de Independiente");
        List<Stop> stops1 = new ArrayList<Stop>(Arrays.asList(stop1, stop2, stop3));
        Route route1 = this.routeService.createRoute("Estadios", 20000, 55.5f, 2, stops1);
        Supplier supplier1 = this.supplierService.createSupplier("Supplier1", "000111");
        Supplier supplier2 = this.supplierService.createSupplier("Supplier2", "000222");
        Service service1 = this.serviceService.createService("Servicio1", 500f, "primer servicio", supplier1);
        Service service2 = this.serviceService.createService("Servicio2", 1000f, "segundo servicio", supplier2);

        Purchase purchase1 = this.purchaseService.createPurchase("100", dyes, route1.getId(), user1.getId());
        assertNotNull(purchase1.getId());
        assertEquals(route1.getPrice(), purchase1.getTotalPrice());
        assertEquals(1, user1.getPurchaseList().size());

        ItemService itemService1 = this.purchaseService.addItemToPurchase(purchase1.getId(), service1.getId(), 1);
        assertNotNull(itemService1.getId());
        assertEquals(supplier1.getId(), itemService1.getService().getId());
        assertEquals(purchase1.getId(), itemService1.getPurchase().getId());
        ItemService itemService2 = this.purchaseService.addItemToPurchase(purchase1.getId(), service2.getId(), 2);

        Optional<Purchase> optionalPurchase1 = this.purchaseService.getPurchaseByCode("100");
        assertTrue(optionalPurchase1.isPresent());
        Purchase purchase3 = optionalPurchase1.get();
        assertEquals(purchase1.getId(), purchase3.getId());
        assertDoesNotThrow(() -> purchase3.getItemServiceList().size());
        assertEquals(2, purchase3.getItemServiceList().size());
        assertEquals(22500, purchase3.getTotalPrice());

        this.purchaseService.createPurchase("101", dyes, route1.getId(), user1.getId());

        assertThrows(ToursException.class, () -> this.purchaseService.createPurchase("200", dyes, route1.getId(), user1.getId()), "No puede realizarse la compra");
        assertThrows(ToursException.class, () -> this.purchaseService.createPurchase("100", route1.getId(), user1.getId()), "Constraint Violation");
    }

    @Test
    void removePurchaseAndItems() throws ToursException {
        User user1 = this.userService.createUser("user1", "1234", "Usuario Uno", "user1@gmail.com", dob1, "000111222333");
        Stop stop1 = this.stopService.createStop("Estadio Monumental", "Estadio de River Plate");
        Stop stop2 = this.stopService.createStop("Estadio La Bombonera", "Estadio de Boca Junions");
        Stop stop3 = this.stopService.createStop("Estadio Libertadores de America", "Estadio de Independiente");
        List<Stop> stops1 = new ArrayList<Stop>(Arrays.asList(stop1, stop2, stop3));
        Route route1 = this.routeService.createRoute("Estadios", 20000, 55.5f, 2, stops1);
        Supplier supplier1 = this.supplierService.createSupplier("Supplier1", "000111");
        Service service1 = this.serviceService.createService("Servicio1", 500f, "primer servicio", supplier1);
        Service service2 = this.serviceService.createService("Servicio2", 1000f, "segundo servicio", supplier1);
        Purchase purchase1 = this.purchaseService.createPurchase("100", dyes, route1.getId(), user1.getId());
        ItemService itemService1 = this.purchaseService.addItemToPurchase(purchase1.getId(), service1.getId(), 1);
        ItemService itemService2 = this.purchaseService.addItemToPurchase(purchase1.getId(), service2.getId(), 2);
        assertEquals(1, service1.getItemServiceList().size());

        this.purchaseService.deletePurchase(purchase1.getId());
        Optional<Purchase> purchase = this.purchaseService.getPurchaseByCode("100");
        assertFalse(purchase.isPresent());
    }

    @Test
    void addReviewToPurchaseTest() throws ToursException {
        User user1 = this.userService.createUser("user1", "1234", "Usuario Uno", "user1@gmail.com", dob1, "000111222333");
        Stop stop1 = this.stopService.createStop("Estadio Monumental", "Estadio de River Plate");
        Stop stop2 = this.stopService.createStop("Estadio La Bombonera", "Estadio de Boca Junions");
        Stop stop3 = this.stopService.createStop("Estadio Libertadores de America", "Estadio de Independiente");
        List<Stop> stops1 = new ArrayList<Stop>(Arrays.asList(stop1, stop2, stop3));
        Route route1 = this.routeService.createRoute("Estadios", 20000, 55.5f, 2, stops1);
        Purchase purchase1 = this.purchaseService.createPurchase("100", dyes, route1.getId(), user1.getId());

        Review review = this.purchaseService.addReviewToPurchase(purchase1.getId(), 5, "un comentario");
        Optional<Purchase> optionalPurchase = this.purchaseService.getPurchaseByCode("100");
        assertTrue(optionalPurchase.isPresent());
        Purchase purchase = optionalPurchase.get();
        assertNotNull(review.getId());
        assertNotNull(purchase.getReview());
        assertEquals(purchase.getId(), review.getPurchase().getId());
    }

    @Test
    void deleteRouteTest() throws ToursException {
        Stop stop1 = this.stopService.createStop("Estadio Monumental", "Estadio de River Plate");
        Stop stop2 = this.stopService.createStop("Estadio La Bombonera", "Estadio de Boca Juniors");
        List<Stop> stops = new ArrayList<>(Arrays.asList(stop1, stop2));
        Route route1 = this.routeService.createRoute("Ruta sin compras", 10000, 20f, 5, stops);
        Route route2 = this.routeService.createRoute("Ruta con compras", 15000, 30f, 5, stops);
        User user1 = this.userService.createUser("user1", "1234", "Usuario Uno", "user1@gmail.com", dob1, "000111222333");
        this.purchaseService.createPurchase("P001", dyes, route2.getId(), user1.getId());

        // Ruta sin compras: debe eliminarse exitosamente
        this.routeService.deleteRouteIfNoSales(route1.getId());
        assertTrue(this.routeService.getRouteById(route1.getId()).isEmpty());

        // Ruta con compras: debe lanzar excepción
        assertThrows(ToursException.class, () -> this.routeService.deleteRouteIfNoSales(route2.getId()), "No puede eliminarse una ruta con compras asociadas");
    }

    @Test
    void deleteUserTest() throws ToursException {
        User user1 = this.userService.createUser("user1", "1234", "Usuario Uno", "user1@gmail.com", dob1, "000111222333");

        assertTrue(user1.isActive());
        this.userService.deleteUser(user1.getId());
        assertTrue(this.userService.getUserByUsername("user1").isEmpty());

        User user2 = this.userService.createUser("user2", "1234", "Usuario Dos", "user2@gmail.com", dob2, "000111222334");
        Stop stop1 = this.stopService.createStop("Estadio Monumental", "Estadio de River Plate");
        Stop stop2 = this.stopService.createStop("Estadio La Bombonera", "Estadio de Boca Junions");
        Stop stop3 = this.stopService.createStop("Estadio Libertadores de America", "Estadio de Independiente");
        List<Stop> stops1 = new ArrayList<Stop>(Arrays.asList(stop1, stop2, stop3));
        Route route1 = this.routeService.createRoute("Estadios", 20000, 55.5f, 2, stops1);
        this.purchaseService.createPurchase("100", dyes, route1.getId(), user2.getId());
        assertTrue(user2.isActive());
        this.userService.deleteUser(user2.getId());
        Optional<User> optionalUser2 = this.userService.getUserByUsername("user2");
        assertTrue(optionalUser2.isPresent());
        User user2b = optionalUser2.get();
        assertFalse(user2b.isActive());

        assertThrows(ToursException.class, () -> this.userService.deleteUser(user2b.getId()), "El usuario se encuentra desactivado");

        TourGuideUser tourGuideUser = this.userService.createTourGuideUser("userG", "1234|", "Usuario TourGuide", "userg@gmail.com", dob2, "000111222555", "edu...");
        Route routeManaged = this.routeService.getRouteById(route1.getId()).orElseThrow(() -> new ToursException("No pudo realizarse la asignación"));
        TourGuideUser tourGuideManaged = (TourGuideUser) this.userService.getUserByUsername(tourGuideUser.getUsername()).orElseThrow(() -> new ToursException("No pudo realizarse la asignación"));
        routeManaged.addTourGuide(tourGuideManaged);
        this.routeService.updateRoute(routeManaged);
        assertTrue(tourGuideUser.isActive());
        assertThrows(ToursException.class, () -> this.userService.deleteUser(tourGuideUser.getId()), "El usuario no puede ser desactivado");
    }

}

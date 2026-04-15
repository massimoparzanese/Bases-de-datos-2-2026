package unlp.info.bd2.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import unlp.info.bd2.model.*;
import unlp.info.bd2.services.PurchaseService;
import unlp.info.bd2.services.RouteService;
import unlp.info.bd2.services.ServiceService;
import unlp.info.bd2.services.StopService;
import unlp.info.bd2.services.SupplierService;
import unlp.info.bd2.services.UserService;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.*;

public class DBInitializer {

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

    @Transactional
    public void prepareDB() throws ToursException {
        Calendar cal1 = Calendar.getInstance();
        cal1.set(1980, Calendar.APRIL, 5);
        Date dob1 = cal1.getTime();
        LocalDate today = LocalDate.now();

        // Suppliers
        Supplier supplier1 = this.supplierService.createSupplier("ABC Tours", "12345");
        Supplier supplier2 = this.supplierService.createSupplier("XYZ Travel Agency", "67890");
        Supplier supplier3 = this.supplierService.createSupplier("Adventure Explorers", "54321");
        Supplier supplier4 = this.supplierService.createSupplier("Sunset Adventures", "98765");
        this.supplierService.createSupplier("Dream Destinations", "24680");

        // Servicios
        Service service1 = this.serviceService.createService("Gaucho Day Local Bakery", 3, "Tortas fritas y churros", supplier1);
        Service service2 = this.serviceService.createService("Souvenir mugs", 7, "Printed Mugs", supplier2);
        Service service3 = this.serviceService.createService("Delta Coffe", 4, "Coffee and tea shop", supplier3);
        this.serviceService.createService("Architectural Expedition Book", 25, "Most famous architectures with details", supplier4);
        Service service5 = this.serviceService.createService("Historical City Tour e-book", 40, "Learn about the city's history", supplier2);
        Service service6 = this.serviceService.createService("souvenir t-shirt", 10, "I love Buenos Aires t-shirt", supplier1);
        Service service7 = this.serviceService.createService("souvenir t-shirt argentina", 10, "I love Argentina t-shirt", supplier1);
        Service service8 = this.serviceService.createService("souvenir photograph", 5, "Souvenir photo at tourist spot", supplier3);
        this.serviceService.createService("souvenir retrato", 5, "Another Souvenir", supplier3);

        // Stops
        Stop stop1 = this.stopService.createStop("Diagonal Norte",	"Obelisco / Catedral Metropolitana / Casa Rosada / Museo del Bicentenario / Galería Güemes / Cabildo de Buenos Aires");
        Stop stop2 = this.stopService.createStop("Avenida de Mayo",	"Casa Rosada / Museo del Bicentenario / Café Tortoni / Manzana de las Luces / Cabildo de Buenos Aires / Teatro Avenida");
        Stop stop3 = this.stopService.createStop("Plaza del Congreso",	"Congreso de la Nación Argentina / Palacio Barolo / Pasaje Rivarola / Hotel Castelar / Monumento a los Dos Congresos");
        Stop stop4 = this.stopService.createStop("Paseo de la Historieta",	"Escultura de Mafalda / Puente de la Mujer / Fragata Sarmiento / Basílica de San Francisco /  Plaza de Mayo");
        Stop stop5 = this.stopService.createStop("Usina del Arte",	"Caminito / Museo Quinquela Martín / Museo del Cine / La Torre del Fantasma / Estadio Boca Juniors");
        Stop stop6 = this.stopService.createStop("Puerto Madero",	"Puente de la Mujer / Buque Escuela Corbeta Uruguay / Museo del Humor / Centro Cultural Néstor Kirchner / Experiencias Náuticas");
        Stop stop7 = this.stopService.createStop("Río de la Plata",	"Reserva Ecológica / Colección de Arte Amalia Lacroze de Fortabat / Circuitos Gastronómicos Peatonales / Centro Cultural Néstor Kirchner / Galerías Pacífico");
        Stop stop8 = this.stopService.createStop("Museo Nacional de Bellas Artes",	"Floralis Genérica / Biblioteca Nacional / Basílica Nuestra Señora del Pilar / Centro Cultural Recoleta / Cementerio de la Recoleta");
        Stop stop9 = this.stopService.createStop("Teatro Colón",	"Obelisco / Teatro Colón / Teatro Cervantes / Galerías Pacífico / Plaza San Martín");
        this.stopService.createStop("Planetario",	"Planetario Galileo Galileo / Museo de Artes Plásticas Sívori / Paseo el Rosedal / Ecoparque Interactivo / Jardín Japonés");
        Stop stop11 = this.stopService.createStop("Bosques de Palermo",	"Mezquita Centro Cultural Islámico Rey Fahd / Campo de Polo / Hipódromo Argentino / Rosedal / Planetario Galileo Galilei");
        this.stopService.createStop("San Telmo",	"Plaza Dorrego / Iglesia San Pedro Telmo / Museo MAMBA / Museo MACBA / Mercado de San Telmo");
        this.stopService.createStop("La Boca - Caminito",	"Usina del Arte / Museo Quinquela Martín / Teatro de la Ribera / Puente Transbordador / Estadio Boca Juniors");
        Stop stop14 = this.stopService.createStop("Belgrano - Barrio Chino",	"Museo Enrique Larreta / Arco Inaugural Barrio Chino / Barrancas de Belgrano / Museo Sarmiento / Iglesia Inmaculada Concepción del Belgrano");
        Stop stop15 = this.stopService.createStop("Recoleta",	"Centro Cultural Recoleta / Cementerio de la Recoleta / Basílica Nuestra Señora del Pilar / Museo Nacional de Bellas Artes / Floralis Genérica");
        this.stopService.createStop("El Monumental (Estadio River Plate)",	"Museo Deportivo River / Jardín de Esculturas / Paseo de las Américas y Converse Skate Plaza / Museo Islas Malvinas e Islas del Atlántico Sur / Centro Cultural de la Memoria Haroldo Conti / Club Hípico Argentino");
        Stop stop17 = this.stopService.createStop("Costanera Sur",	"Paseo de la Gloria / Monumento al Tango / Puente de la Mujer / Fragata Sarmiento / Parque Micaela Bastidas");
        Stop stop18 = this.stopService.createStop("Av 9 de Julio",	"Peatonal Florida / Teatro Colón / Plaza San Martín / Galerías Pacífico / Museo de Arte Hispanoamericano Isaac Fernández Blanco");
        Stop stop19 = this.stopService.createStop("Plaza Italia",	"Museo Evita / Jardín Japonés / Jardín Botánico Carlos Thays / Rosedal / Ecoparque Interactivo");
        Stop stop20 = this.stopService.createStop("Delta",	"Delta / Tigre");

        // Users
        User user1 = this.userService.createUser("user1", "1234", "Usuario Uno", "user1@gmail.com", dob1, "000111222333");
        User user2 = this.userService.createUser("user2", "1234", "Usuario Dos", "user2@gmail.com", dob1, "000111222333");
        User user3 = this.userService.createUser("user3", "1234", "Usuario Tres", "user3@gmail.com", dob1, "000111222333");
        User user4 = this.userService.createUser("user4", "1234", "Usuario Cuatro", "user4@gmail.com", dob1, "000111222333");
        this.userService.createUser("user5", "1234", "Usuario Cinco", "user5@gmail.com", dob1, "000111222333"); // USUARIO SIN COMPRAS
        User user6 = this.userService.createUser("user6", "1234", "Usuario Seis", "user6@gmail.com", dob1, "000111222333");
        User user7 = this.userService.createUser("user7", "1234", "Usuario Siete", "user7@gmail.com", dob1, "000111222333");
        User user8 = this.userService.createUser("user8", "1234", "Usuario Ocho", "user8@gmail.com", dob1, "000111222333");
        User user9 = this.userService.createUser("user9", "1234", "Usuario Nueve", "user9@gmail.com", dob1, "000111222333");
        User user10 = this.userService.createUser("user10", "1234", "Usuario Diez", "user10@gmail.com", dob1, "000111222333");

        // Driver Users
        DriverUser driverUser1 = this.userService.createDriverUser("userD1", "1234", "Usuario Driver", "userd@gmail.com", dob1, "000111222444", "exp...");
        DriverUser driverUser2 = this.userService.createDriverUser("userD2", "1234", "Usuario Driver2", "userd2@gmail.com", dob1, "000111222444", "exp...");
        DriverUser driverUser3 = this.userService.createDriverUser("userD3", "1234", "Usuario Driver3", "userd3@gmail.com", dob1, "000111222444", "exp...");
        DriverUser driverUser4 = this.userService.createDriverUser("userD4", "1234", "Usuario Driver4", "userd4@gmail.com", dob1, "000111222444", "exp...");

        // TourGuide Users
        TourGuideUser tourGuideUser1 = this.userService.createTourGuideUser("userG1", "1234", "Usuario TourGuide", "userg@gmail.com", dob1, "000111222555", "edu...");
        TourGuideUser tourGuideUser2 = this.userService.createTourGuideUser("userG2", "1234", "Usuario TourGuide2", "userg2@gmail.com", dob1, "000111222555", "edu...");
        TourGuideUser tourGuideUser3 = this.userService.createTourGuideUser("userG3", "1234", "Usuario TourGuide3", "userg3@gmail.com", dob1, "000111222555", "edu...");
        TourGuideUser tourGuideUser4 = this.userService.createTourGuideUser("userG4", "1234", "Usuario TourGuide4", "userg4@gmail.com", dob1, "000111222555", "edu...");

        // Routes
        List<Stop> stopsRoute1 = new ArrayList<Stop>(Arrays.asList(stop1, stop2, stop3, stop4, stop6, stop14, stop15, stop17, stop19));
        Route route1 = this.routeService.createRoute("City Tour", 200, 62,10, stopsRoute1);
        route1.addDriver(driverUser1);
        route1.addDriver(driverUser2);
        route1.addTourGuide(tourGuideUser1);

        List<Stop> stopsRoute2 = new ArrayList<Stop>(Arrays.asList(stop2, stop3, stop7, stop9, stop18, stop19));
        Route route2 = this.routeService.createRoute("Historical Adventure", 300, 68,10, stopsRoute2);
        route2.addDriver(driverUser2);
        route2.addDriver(driverUser3);
        route2.addTourGuide(tourGuideUser2);
        route2.addTourGuide(tourGuideUser3);

        List<Stop> stopsRoute3 = new ArrayList<Stop>(Arrays.asList(stop5, stop6, stop8,  stop9, stop14, stop15));
        Route route3 = this.routeService.createRoute("Architectural Expedition", 500, 55,15, stopsRoute3);
        route3.addDriver(driverUser3);
        route3.addTourGuide(tourGuideUser3);

        List<Stop> stopsRoute4 = new ArrayList<Stop>(Arrays.asList(stop7, stop11, stop20));
        Route route4 = this.routeService.createRoute("Delta Tour", 800, 75,10, stopsRoute4);
        route4.addDriver(driverUser4);
        route4.addTourGuide(tourGuideUser1);
        route4.addTourGuide(tourGuideUser4);

        List<Stop> stopsRoute5 = new ArrayList<>(Arrays.asList(stop1, stop2));
        this.routeService.createRoute("Ruta vacia", 900, 20, 5, stopsRoute5);

        // Purchases
        Purchase purchase1 = this.purchaseService.createPurchase("P001", java.sql.Date.valueOf(today.minusDays(30)), route1.getId(), user1.getId());
        Purchase purchase2 = this.purchaseService.createPurchase("P002", java.sql.Date.valueOf(today.minusDays(30)), route2.getId(), user2.getId());
        Purchase purchase3 = this.purchaseService.createPurchase("P003", java.sql.Date.valueOf(today.minusDays(28)), route3.getId(), user3.getId());
        Purchase purchase4 = this.purchaseService.createPurchase("P004", java.sql.Date.valueOf(today.minusDays(27)), route4.getId(), user4.getId());
        Purchase purchase5 = this.purchaseService.createPurchase("P005", java.sql.Date.valueOf(today.minusDays(27)), route1.getId(), user1.getId());
        Purchase purchase6 = this.purchaseService.createPurchase("P006", java.sql.Date.valueOf(today.minusDays(21)), route2.getId(), user2.getId());
        Purchase purchase7 = this.purchaseService.createPurchase("P007", java.sql.Date.valueOf(today.minusDays(20)), route3.getId(), user6.getId());
        Purchase purchase8 = this.purchaseService.createPurchase("P008", java.sql.Date.valueOf(today.minusDays(20)), route4.getId(), user7.getId());
        Purchase purchase9 = this.purchaseService.createPurchase("P009", java.sql.Date.valueOf(today.minusDays(20)), route1.getId(), user1.getId());
        Purchase purchase10 = this.purchaseService.createPurchase("P010", java.sql.Date.valueOf(today.minusDays(19)), route2.getId(), user2.getId());
        Purchase purchase11 = this.purchaseService.createPurchase("P011", java.sql.Date.valueOf(today.minusDays(18)), route1.getId(), user1.getId());
        Purchase purchase12 = this.purchaseService.createPurchase("P012", java.sql.Date.valueOf(today.minusDays(15)), route2.getId(), user8.getId());
        Purchase purchase13 = this.purchaseService.createPurchase("P013", java.sql.Date.valueOf(today.minusDays(14)), route3.getId(), user9.getId());
        Purchase purchase14 = this.purchaseService.createPurchase("P014", java.sql.Date.valueOf(today.minusDays(12)), route4.getId(), user10.getId());
        Purchase purchase15 = this.purchaseService.createPurchase("P015", java.sql.Date.valueOf(today.minusDays(12)), route1.getId(), user1.getId());
        Purchase purchase16 = this.purchaseService.createPurchase("P016", java.sql.Date.valueOf(today.minusDays(11)), route2.getId(), user10.getId());
        Purchase purchase18 = this.purchaseService.createPurchase("P018", java.sql.Date.valueOf(today.minusDays(10)), route3.getId(), user7.getId());
        Purchase purchase19 = this.purchaseService.createPurchase("P019", java.sql.Date.valueOf(today.minusDays(10)), route1.getId(), user1.getId());
        Purchase purchase17 = this.purchaseService.createPurchase("P017", java.sql.Date.valueOf(today.minusDays(8)), route3.getId(), user6.getId());
        Purchase purchase20 = this.purchaseService.createPurchase("P020", java.sql.Date.valueOf(today.minusDays(7)), route2.getId(), user2.getId());

        this.purchaseService.addItemToPurchase(purchase4.getId(), service1.getId(), 1);
        this.purchaseService.addItemToPurchase(purchase4.getId(), service5.getId(), 2);
        this.purchaseService.addItemToPurchase(purchase1.getId(), service3.getId(), 1);
        this.purchaseService.addItemToPurchase(purchase2.getId(), service8.getId(), 2);
        this.purchaseService.addItemToPurchase(purchase10.getId(), service7.getId(), 1);
        this.purchaseService.addItemToPurchase(purchase9.getId(), service6.getId(), 2);
        this.purchaseService.addItemToPurchase(purchase8.getId(), service1.getId(), 1);
        this.purchaseService.addItemToPurchase(purchase7.getId(), service2.getId(), 1);
        this.purchaseService.addItemToPurchase(purchase1.getId(), service1.getId(), 2);
        this.purchaseService.addItemToPurchase(purchase20.getId(), service6.getId(), 2);
        this.purchaseService.addItemToPurchase(purchase19.getId(), service3.getId(), 1);
        this.purchaseService.addItemToPurchase(purchase18.getId(), service8.getId(), 3);
        this.purchaseService.addItemToPurchase(purchase17.getId(), service6.getId(), 1);
        this.purchaseService.addItemToPurchase(purchase17.getId(), service6.getId(), 3);
        this.purchaseService.addItemToPurchase(purchase16.getId(), service1.getId(), 1);
        this.purchaseService.addItemToPurchase(purchase15.getId(), service2.getId(), 1);
        this.purchaseService.addItemToPurchase(purchase14.getId(), service1.getId(), 1);
        this.purchaseService.addItemToPurchase(purchase14.getId(), service5.getId(), 2);
        this.purchaseService.addItemToPurchase(purchase13.getId(), service3.getId(), 1);
        this.purchaseService.addItemToPurchase(purchase12.getId(), service8.getId(), 3);
        this.purchaseService.addItemToPurchase(purchase10.getId(), service7.getId(), 1);
        this.purchaseService.addItemToPurchase(purchase11.getId(), service6.getId(), 2);
        this.purchaseService.addItemToPurchase(purchase1.getId(), service1.getId(), 1);
        this.purchaseService.addItemToPurchase(purchase5.getId(), service6.getId(), 1);

        this.purchaseService.addReviewToPurchase(purchase1.getId(), 4, "Excelente recorrido, me encantó la experiencia.");
        this.purchaseService.addReviewToPurchase(purchase2.getId(), 5, "Increíble tour, lo recomendaría a cualquiera.");
        this.purchaseService.addReviewToPurchase(purchase3.getId(), 3, "Buen recorrido, pero podría mejorar la organización.");
        this.purchaseService.addReviewToPurchase(purchase4.getId(), 2, "No quedé satisfecho con el tour, esperaba más.");
        this.purchaseService.addReviewToPurchase(purchase5.getId(), 5, "¡Una experiencia inolvidable! Definitivamente volvería a hacerlo.");
        this.purchaseService.addReviewToPurchase(purchase6.getId(), 4, "Me encantó el recorrido, el guía fue muy amable.");
        this.purchaseService.addReviewToPurchase(purchase7.getId(), 3, "Estuvo bien, pero no cumplió completamente mis expectativas.");
        this.purchaseService.addReviewToPurchase(purchase8.getId(), 1, "Terrible experiencia, no recomendaría este tour a nadie.");
        this.purchaseService.addReviewToPurchase(purchase9.getId(), 4, "No esperaba menos");
        this.purchaseService.addReviewToPurchase(purchase12.getId(), 5, "Lo haría 10 veces más");
        this.purchaseService.addReviewToPurchase(purchase13.getId(), 3, "Nada del otro mundo.");
        this.purchaseService.addReviewToPurchase(purchase14.getId(), 2, "Varios inconvenientes en el viaje, incluyendo cambio de horario de salida.");
        this.purchaseService.addReviewToPurchase(purchase15.getId(), 5, "Volvería a hacerlo.");
        this.purchaseService.addReviewToPurchase(purchase16.getId(), 3, "Me encantó el tour, pero el guía no era ductil con el portugués.");
        this.purchaseService.addReviewToPurchase(purchase17.getId(), 3, "Estuvo bien, aunque esperaba más.");
        this.purchaseService.addReviewToPurchase(purchase18.getId(), 1, "Muy caro para lo que se brinda.");
    }
}

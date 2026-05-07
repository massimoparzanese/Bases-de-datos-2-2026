package unlp.info.bd2.services.Impl;

import org.springframework.transaction.annotation.Transactional;
import unlp.info.bd2.model.*;
import unlp.info.bd2.repositories.PurchaseRepository;
import unlp.info.bd2.repositories.RouteRepository;
import unlp.info.bd2.repositories.*;
import unlp.info.bd2.services.PurchaseService;
import unlp.info.bd2.utils.ToursException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Implementación de servicios para operaciones sobre compras.
 */
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;

    private final RouteRepository routeRepository;

    private final UserRepository userRepository;

    private final ServiceRepository serviceRepository;

    private final ItemServiceRepository itemServiceRepository;

    private final ReviewRepository reviewRepository;

    public PurchaseServiceImpl(PurchaseRepository purchaseRepository,
                               RouteRepository routeRepository,
                               UserRepository userRepository,
                               ServiceRepository serviceRepository,
                               ItemServiceRepository itemServiceRepository,
                               ReviewRepository reviewRepository) {
        this.purchaseRepository = purchaseRepository;
        this.routeRepository = routeRepository;
        this.userRepository = userRepository;
        this.serviceRepository = serviceRepository;
        this.itemServiceRepository = itemServiceRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    @Transactional(rollbackFor = ToursException.class)
    public Purchase createPurchase(String code, Long routeId, Long userId) throws ToursException {
        return this.createPurchase(code, new Date(), routeId, userId);
    }

    @Override
    @Transactional(rollbackFor = ToursException.class)
    public Purchase createPurchase(String code, Date date, Long routeId, Long userId) throws ToursException {
        try {

            Route route = routeRepository.findById(routeId)
                    .orElseThrow(() -> new ToursException("No existe la ruta"));
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ToursException("No existe el usuario"));

                long purchasesForRoute = purchaseRepository.countByRouteId(routeId);

            if (purchasesForRoute >= route.getMaxNumberUsers()) {
                throw new ToursException("No puede realizarse la compra");
            }

            Purchase purchase = new Purchase(code, route.getPrice(), date, user, route);
            Purchase savedPurchase = purchaseRepository.save(purchase);

            if (user.getPurchaseList() != null) {
                user.getPurchaseList().add(savedPurchase);
                userRepository.save(user);
            }

            return savedPurchase;
        } catch (ToursException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo crear la compra");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Purchase> getPurchaseById(Long id) throws ToursException {
        try {
            return purchaseRepository.findById(id);
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo buscar la compra por id");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Purchase> getPurchaseByCode(String code) throws ToursException {
        try {
            return purchaseRepository.findByCode(code);
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo buscar la compra por codigo");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Purchase> getAllPurchases() throws ToursException {
        try {
            return purchaseRepository.findAll();
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo listar las compras");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Purchase> getAllPurchasesOfUsername(String username) throws ToursException {
        try {
            return purchaseRepository.getAllPurchasesOfUsername(username);
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo obtener las compras del usuario");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Purchase> getPurchasesByUser(Long userId) throws ToursException {
        try {
            return purchaseRepository.findByUserId(userId);
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo obtener las compras del usuario");
        }
    }

    @Override
    @Transactional(rollbackFor = ToursException.class)
    public Purchase updatePurchase(Purchase purchase) throws ToursException {
        if (purchase == null || purchase.getId() == null) {
            throw new ToursException("La compra a actualizar debe tener id");
        }

        try {
            return purchaseRepository.save(purchase);
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo actualizar la compra");
        }
    }

    @Override
    @Transactional(rollbackFor = ToursException.class)
    public void deletePurchase(Long purchaseId) throws ToursException {
        try {
            Purchase purchase = purchaseRepository.findById(purchaseId)
                    .orElseThrow(() -> new ToursException("No existe una compra con id " + purchaseId));
            if (purchase.getUser() != null && purchase.getUser().getPurchaseList() != null) {
                purchase.getUser().getPurchaseList().remove(purchase);
            }
            purchaseRepository.delete(purchase);
        } catch (ToursException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo eliminar la compra");
        }
    }

    @Override
    @Transactional(rollbackFor = ToursException.class)
    public ItemService addItemToPurchase(Long purchaseId, Long serviceId, int quantity) throws ToursException {
        if (quantity <= 0) {
            throw new ToursException("La cantidad debe ser mayor a cero");
        }

        try {
            Purchase purchase = purchaseRepository.findById(purchaseId)
                    .orElseThrow(() -> new ToursException("No existe la compra"));
            Service service = serviceRepository.findById(serviceId)
                    .orElseThrow(() -> new ToursException("No existe el servicio"));

            ItemService itemService = new ItemService(quantity, purchase, service);
            ItemService savedItem = itemServiceRepository.save(itemService);

            purchase.addItem(savedItem);
            purchaseRepository.save(purchase);

            if (service.getItemServiceList() != null) {
                service.getItemServiceList().add(savedItem);
                serviceRepository.save(service);
            }

            return savedItem;
        } catch (ToursException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo agregar el item a la compra");
        }
    }

    @Override
    @Transactional(rollbackFor = ToursException.class)
    public void removeItemFromPurchase(Long purchaseId, Long itemServiceId) throws ToursException {
        try {
            Purchase purchase = purchaseRepository.findById(purchaseId)
                    .orElseThrow(() -> new ToursException("No existe la compra"));

            ItemService item = itemServiceRepository.findById(itemServiceId)
                    .orElseThrow(() -> new ToursException("No existe el item de servicio"));

            if (item.getPurchase() == null || !item.getPurchase().getId().equals(purchaseId)) {
                throw new ToursException("El item no pertenece a la compra indicada");
            }

            purchase.setTotalPrice((float) (purchase.getTotalPrice() - item.getPrice()));
            if (purchase.getItemServiceList() != null) {
                purchase.getItemServiceList().removeIf(i -> i.getId().equals(itemServiceId));
            }
            purchaseRepository.save(purchase);
            itemServiceRepository.delete(item);
        } catch (ToursException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo remover el item de la compra");
        }
    }

    @Override
    @Transactional(rollbackFor = ToursException.class)
    public Review addReviewToPurchase(Long purchaseId, int rating, String comment) throws ToursException {
        if (rating < 1 || rating > 5) {
            throw new ToursException("La calificacion debe estar entre 1 y 5");
        }

        try {
            Purchase purchase = purchaseRepository.findById(purchaseId)
                    .orElseThrow(() -> new ToursException("No existe la compra"));

            if (purchase.getReview() != null) {
                throw new ToursException("La compra ya tiene una resena asociada");
            }

            Review review = new Review(rating, comment, purchase);
            Review savedReview = reviewRepository.save(review);
            purchase.setReview(savedReview);
            purchaseRepository.save(purchase);
            return savedReview;
        } catch (ToursException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo agregar la resena a la compra");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Review> getReviewByPurchase(Long purchaseId) throws ToursException {
        try {
            Optional<Purchase> purchase = purchaseRepository.findById(purchaseId);
            return purchase.map(Purchase::getReview);
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo obtener la resena de la compra");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public int getCountOfPurchasesBetweenDates(Date start, Date end) throws ToursException{
        
        try{
            return purchaseRepository.getCountOfPurchasesBetweenDates(start, end);
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo obtener el conteo de compras entre fechas");
        }
    }
}

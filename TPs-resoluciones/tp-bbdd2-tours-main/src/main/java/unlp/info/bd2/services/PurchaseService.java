package unlp.info.bd2.services;

import unlp.info.bd2.model.ItemService;
import unlp.info.bd2.model.Purchase;
import unlp.info.bd2.model.Review;
import unlp.info.bd2.utils.ToursException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicios para operaciones sobre compras.
 * Gestiona creación, actualización, búsqueda, eliminación de compras,
 * así como administración de items y reseñas asociadas.
 */
public interface PurchaseService {

    Purchase createPurchase(String code, Long routeId, Long userId) throws ToursException;

    Purchase createPurchase(String code, Date date, Long routeId, Long userId) throws ToursException;

    Optional<Purchase> getPurchaseById(Long id) throws ToursException;

    Optional<Purchase> getPurchaseByCode(String code) throws ToursException;

    List<Purchase> getAllPurchases() throws ToursException;

    List<Purchase> getPurchasesByUser(Long userId) throws ToursException;

    Purchase updatePurchase(Purchase purchase) throws ToursException;

    void deletePurchase(Long purchaseId) throws ToursException;

    ItemService addItemToPurchase(Long purchaseId, Long serviceId, int quantity) throws ToursException;

    void removeItemFromPurchase(Long purchaseId, Long itemServiceId) throws ToursException;

    Review addReviewToPurchase(Long purchaseId, int rating, String comment) throws ToursException;

    Optional<Review> getReviewByPurchase(Long purchaseId) throws ToursException;
}

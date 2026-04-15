package unlp.info.bd2.services;

import unlp.info.bd2.model.User;
import unlp.info.bd2.model.DriverUser;
import unlp.info.bd2.model.TourGuideUser;
import unlp.info.bd2.utils.ToursException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicios para operaciones sobre usuarios.
 * Gestiona creación, actualización y búsqueda de User, DriverUser y TourGuideUser.
 */
public interface UserService {

    User createUser(String username, String password, String name, String email, Date birthdate, String phoneNumber) throws ToursException;

    DriverUser createDriverUser(String username, String password, String name, String email, Date birthdate, String phoneNumber, String expedient) throws ToursException;

    TourGuideUser createTourGuideUser(String username, String password, String name, String email, Date birthdate, String phoneNumber, String education) throws ToursException;

    Optional<User> getUserById(Long id) throws ToursException;

    Optional<User> getUserByUsername(String username) throws ToursException;

    User updateUser(User user) throws ToursException;

    void deleteUser(Long userId) throws ToursException;

    List<User> getAllUsers() throws ToursException;

    List<User> getUserSpendingMoreThan(float mount) throws ToursException;

    List<TourGuideUser> getTourGuidesWithRating1() throws ToursException;
}

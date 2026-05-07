package unlp.info.bd2.services.Impl;

import org.springframework.transaction.annotation.Transactional;
import unlp.info.bd2.model.DriverUser;
import unlp.info.bd2.model.TourGuideUser;
import unlp.info.bd2.model.User;
import unlp.info.bd2.repositories.RouteRepository;
import unlp.info.bd2.repositories.UserRepository;
import unlp.info.bd2.services.UserService;
import unlp.info.bd2.utils.ToursException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Implementación de servicios para operaciones sobre usuarios.
 */
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RouteRepository routeRepository;

    public UserServiceImpl(UserRepository userRepository,
            RouteRepository routeRepository) {
        this.userRepository = userRepository;
        this.routeRepository = routeRepository;
    }

    @Transactional(rollbackFor = ToursException.class)
    public User createUser(String username, String password, String name, String email, Date birthdate, String phoneNumber) throws ToursException {
        try {
            User user = new User(username, password, name, email, birthdate, phoneNumber);
            user.setPurchaseList(new ArrayList<>());
            user.setActive(true);
            return userRepository.save(user);
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo crear el usuario");
        }
    }

    @Override
    @Transactional(rollbackFor = ToursException.class)
    public DriverUser createDriverUser(String username, String password, String name, String email, Date birthdate, String phoneNumber, String expedient) throws ToursException {
        try {
            DriverUser driverUser = new DriverUser(username, password, name, email, birthdate, phoneNumber, expedient);
            driverUser.setPurchaseList(new ArrayList<>());
            driverUser.setActive(true);
            return (DriverUser) userRepository.save(driverUser);
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo crear el conductor");
        }
    }

    @Override
    @Transactional(rollbackFor = ToursException.class)
    public TourGuideUser createTourGuideUser(String username, String password, String name, String email, Date birthdate, String phoneNumber, String education) throws ToursException {
        try {
            TourGuideUser tourGuideUser = new TourGuideUser(username, password, name, email, birthdate, phoneNumber, education);
            tourGuideUser.setPurchaseList(new ArrayList<>());
            tourGuideUser.setActive(true);
            return (TourGuideUser) userRepository.save(tourGuideUser);
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo crear el guia turistico");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id) throws ToursException {
        try {
            return userRepository.findById(id);
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo buscar el usuario por id");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByUsername(String username) throws ToursException {
        try {
            return userRepository.findByUsername(username);
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo buscar el usuario por username");
        }
    }

    @Override
    @Transactional(rollbackFor = ToursException.class)
    public User updateUser(User user) throws ToursException {
        if (user == null || user.getId() == null) {
            throw new ToursException("El usuario a actualizar debe tener id");
        }
        try {
            String originalUsername = userRepository.findById(user.getId())
                    .map(User::getUsername)
                    .orElseThrow(RuntimeException::new);
            user.setUsername(originalUsername);
            return userRepository.save(user);
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo actualizar el usuario");
        }
    }

    @Override
    @Transactional(rollbackFor = ToursException.class)
    public void deleteUser(Long userId) throws ToursException {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ToursException("No existe un usuario con id " + userId));

            if (!user.isActive()) {
                throw new ToursException("El usuario se encuentra desactivado");
            }

            if (user instanceof TourGuideUser) {
                boolean assignedToSomeRoute = routeRepository.existsByTourGuideList_Id(userId);
                if (assignedToSomeRoute) {
                    throw new ToursException("El usuario no puede ser desactivado");
                }
            }

            if (user.getPurchaseList() != null && !user.getPurchaseList().isEmpty()) {
                user.setActive(false);
                userRepository.save(user);
                return;
            }

            userRepository.delete(user);
        } catch (ToursException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo eliminar el usuario");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() throws ToursException {
        try {
            return (List<User>) userRepository.findAll();
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo listar los usuarios");
        }
    }

    @Transactional(readOnly = true)
    public List<User> getUserSpendingMoreThan(float mount) throws ToursException {
        try {
            return userRepository.getUserSpendingMoreThan(mount);
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo buscar los usuarios por gasto");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TourGuideUser> getTourGuidesWithRating1() throws ToursException {
        try {
            return userRepository.getTourGuidesWithRating1();
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo obtener guias con rating 1");
        }
    }
}

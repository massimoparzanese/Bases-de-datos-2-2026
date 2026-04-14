package unlp.info.bd2.services.Impl;

import jakarta.transaction.Transactional;
import unlp.info.bd2.model.DriverUser;
import unlp.info.bd2.model.TourGuideUser;
import unlp.info.bd2.model.User;
import unlp.info.bd2.repositories.Impl.UsersRepositoryImpl;
import unlp.info.bd2.services.UserService;
import unlp.info.bd2.utils.ToursException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Implementación de servicios para operaciones sobre usuarios.
 */
public class UserServiceImpl implements UserService {

    private final UsersRepositoryImpl userRepository;

    public UserServiceImpl(UsersRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(rollbackOn = ToursException.class)
    public User createUser(String username, String password, String name, String email, Date birthdate, String phoneNumber) throws ToursException {
        try {
            User user = new User(username, password, name, email, birthdate, phoneNumber);
            return userRepository.save(user);
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo crear el usuario");
        }
    }

    @Override
    @Transactional(rollbackOn = ToursException.class)
    public DriverUser createDriverUser(String username, String password, String name, String email, Date birthdate, String phoneNumber, String expedient) throws ToursException {
        try {
            DriverUser driverUser = new DriverUser(username, password, name, email, birthdate, phoneNumber, expedient);
            return (DriverUser) userRepository.save(driverUser);
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo crear el conductor");
        }
    }

    @Override
    @Transactional(rollbackOn = ToursException.class)
    public TourGuideUser createTourGuideUser(String username, String password, String name, String email, Date birthdate, String phoneNumber, String education) throws ToursException {
        try {
            TourGuideUser tourGuideUser = new TourGuideUser(username, password, name, email, birthdate, phoneNumber, education);
            return (TourGuideUser) userRepository.save(tourGuideUser);
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo crear el guia turistico");
        }
    }

    @Override
    @Transactional(rollbackOn = ToursException.class)
    public Optional<User> getUserById(Long id) throws ToursException {
        try {
            return userRepository.findById(id);
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo buscar el usuario por id");
        }
    }

    @Override
    @Transactional(rollbackOn = ToursException.class)
    public Optional<User> getUserByUsername(String username) throws ToursException {
        try {
            return userRepository.findAll()
                    .stream()
                    .filter(u -> u.getUsername() != null && u.getUsername().equals(username))
                    .findFirst();
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo buscar el usuario por username");
        }
    }

    @Override
    @Transactional(rollbackOn = ToursException.class)
    public User updateUser(User user) throws ToursException {
        if (user == null || user.getId() == null) {
            throw new ToursException("El usuario a actualizar debe tener id");
        }
        try {
            return userRepository.save(user);
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo actualizar el usuario");
        }
    }

    @Override
    @Transactional(rollbackOn = ToursException.class)
    public void deleteUser(Long userId) throws ToursException {
        try {
            Optional<User> user = userRepository.findById(userId);
            if (user.isEmpty()) {
                throw new ToursException("No existe un usuario con id " + userId);
            }
            userRepository.delete(user.get());
        } catch (ToursException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo eliminar el usuario");
        }
    }

    @Override
    @Transactional(rollbackOn = ToursException.class)
    public List<User> getAllUsers() throws ToursException {
        try {
            return userRepository.findAll();
        } catch (RuntimeException ex) {
            throw new ToursException("No se pudo listar los usuarios");
        }
    }
}

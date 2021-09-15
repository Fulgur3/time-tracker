package com.model.service;

import com.controller.dto.RegistrationUserDTO;
import com.controller.dto.UpdateUserDTO;
import com.model.dao.DaoConnection;
import com.model.dao.DaoFactory;
import com.model.dao.UserDao;
import com.model.entity.Authority;
import com.model.entity.User;
import com.util.exception.DaoException;
import com.util.exception.UsernameNotUniqueException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Service with business logic for managing users
 */
public class UserService {
    private static final Logger log = LogManager.getLogger();
    private final DaoFactory daoFactory = DaoFactory.getInstance();

    public Optional<User> findUserByUsername(String username) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            log.info("Trying to get user by username: " + username);
            return userDao.findByUsername(username);
        } catch (DaoException e) {
            log.warn("Can not find user with username: " + username);
            return Optional.empty();
        }
    }

    public void registerUser(RegistrationUserDTO userDTO) throws UsernameNotUniqueException {
        User user = User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .username(userDTO.getUsername())
                .password(DigestUtils.md5Hex(userDTO.getPassword()))
                .authorities(Collections.singleton(Authority.USER))
                .build();
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);

            userDao.create(user);
            log.info("User {} successfully registered", user.getUsername());
        } catch (DaoException e) {
            log.warn("Can not register user: " + user);
            if (e.getCause() instanceof SQLException) {
                SQLException causeException = (SQLException) e.getCause();
                System.out.println(causeException.getErrorCode());
                if (causeException.getSQLState().equals("23505")) {
                    log.warn("Login {} not unique", user.getUsername());
                    throw new UsernameNotUniqueException();
                }
            }
        }
    }

    public List<User> getAllUsersPageable(int page, int size) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            return userDao.findAllPageable(page, size);
        } catch (DaoException e) {
            log.warn("Can not get all users", e);
            return Collections.emptyList();
        }
    }

    public void deleteUserById(long id) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            userDao.delete(id);
        } catch (DaoException e) {
            log.warn("Can not delete user");
        }
    }

    public User getUserById(long id) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            return userDao.findById(id).orElseThrow(() ->
                    new IllegalArgumentException("Invalid user id: " + id));
        } catch (DaoException e) {
            log.warn("Can not find user with id: " + id);
        }
        return null;
    }

    public void updateUser(UpdateUserDTO userDTO) throws UsernameNotUniqueException {
        User user = User.builder()
                .id(userDTO.getId())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .password(DigestUtils.md5Hex(userDTO.getPassword()))
                .username(userDTO.getUsername())
                .authorities(userDTO.getAuthorities())
                .build();
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            if (userDTO.getPassword()==null) {
                String oldPassword = getUserById(userDTO.getId()).getPassword();
                user.setPassword(oldPassword);
            }
            userDao.update(user);
        } catch (DaoException e) {
            log.warn("Can not update user " + user.getUsername(), e);
            if (e.getCause() instanceof SQLException) {
                SQLException causeException = (SQLException) e.getCause();
                if (causeException.getSQLState().equals("23505")) {
                    log.warn("Login {} not unique", user.getUsername());
                    throw new UsernameNotUniqueException(e);
                }
            }
        }
    }

    public long getNumberOfRecords() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            return userDao.getNumberOfRecords();
        } catch (DaoException e) {
            log.warn("Can not get number of users", e);
        }
        return 0;
    }
}

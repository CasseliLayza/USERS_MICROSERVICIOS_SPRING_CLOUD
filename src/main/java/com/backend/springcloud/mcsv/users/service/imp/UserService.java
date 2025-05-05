package com.backend.springcloud.mcsv.users.service.imp;

import com.backend.springcloud.mcsv.users.entity.Role;
import com.backend.springcloud.mcsv.users.entity.User;
import com.backend.springcloud.mcsv.users.exception.DuplicateUserException;
import com.backend.springcloud.mcsv.users.exception.ResourceNotFoundException;
import com.backend.springcloud.mcsv.users.repository.RoleRepository;
import com.backend.springcloud.mcsv.users.repository.UserRepository;
import com.backend.springcloud.mcsv.users.service.IUserService;
import org.slf4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }


    @Override
    @Transactional
    public User createUser(User user) {
        LOGGER.info("Creating user: --> {}", user);
        userRepository.findByUsername(user.getUsername())
                .ifPresent(u -> {
                    throw new DuplicateUserException("Username already exists: " + user.getUsername());
                });

        userRepository.findByEmail(user.getEmail())
                .ifPresent(u -> {
                    throw new DuplicateUserException("Email already exists: " + user.getEmail());
                });

        user.setEnable(user.isEnable() == null || user.isEnable());
        user.setRoles(getRoles(user));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(user.getCreatedAt() == null ? LocalDate.now() : user.getCreatedAt());

        User savedUser = userRepository.save(user);
        LOGGER.info("User created: --> {}", savedUser);
        return savedUser;
    }


    @Override
    @Transactional(readOnly = true)
    public List<User> findUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findUser(Long id) throws ResourceNotFoundException {
        LOGGER.info("Finding user with id: --> {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        LOGGER.info("User found: --> {}", user);
        return user;
    }

    @Override
    @Transactional
    public User updateUser(Long id, User user) throws ResourceNotFoundException {
        LOGGER.info("Updating user with id: --> {}", id);
        userRepository.findByUsername(user.getUsername())
                .ifPresent(u -> {
                    if (!u.getId().equals(id)) {
                        throw new DuplicateUserException("Username already exists: " + user.getUsername());
                    }
                });
        userRepository.findByEmail(user.getEmail())
                .ifPresent(u -> {
                    if (!u.getId().equals(id)) {
                        throw new DuplicateUserException("Email already exists: " + user.getEmail());
                    }
                });

        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setName(user.getName());
                    existingUser.setUsername(user.getUsername());
                    existingUser.setEmail(user.getEmail());
                    existingUser.setRoles(getRoles(user));
                    existingUser.setEnable(user.isEnable() == null || user.isEnable());
                    existingUser.setCreatedAt(user.getCreatedAt() == null ? LocalDate.now() : user.getCreatedAt());

                    LOGGER.info("Updating user: --> {}", existingUser);
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        LOGGER.info("Deleting user with id: --> {}", id);
        userRepository.findById(id)
                .ifPresentOrElse(userRepository::delete,
                        () -> {
                            throw new RuntimeException(new ResourceNotFoundException("User not found with id: " + id));
                        });
        LOGGER.info("User deleted with id: --> {}", id);
    }

    public User findByUsername(String username) throws ResourceNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        LOGGER.info("User found: --> {}", user);
        return user;
    }

    private List<Role> getRoles(User user) {
        List<Role> roles = new ArrayList<>();
        Optional<Role> roleOptional = roleRepository.findByName("ROLE_USER");
        roleOptional.ifPresent(roles::add);

        if (user.isAdmin()) {
            Optional<Role> adminRoleOptional = roleRepository.findByName("ROLE_ADMIN");
            adminRoleOptional.ifPresent(roles::add);
        }

        return roles;
    }
}

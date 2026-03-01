package com.notes.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.notes.app.repository.UserRepository;
import com.notes.app.model.User;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {
    
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Регистрация пользователя
    public User register(String username, String password) {

        log.info("Попытка регистрации пользователя: {}", username);

        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();

        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setCreatedAt(LocalDateTime.now());

        log.info("Пользователь {} зарегистрирован с ID: {}", username, user.getId());

        return userRepository.save(user);
    }

    // Поиск пользователя по username
    public User findByUsername(String username) {
        
        log.debug("Поиск пользователя по username: {}", username);
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            log.warn("Пользователь с username {} не найден", username);
        } else {
            log.debug("Пользователь найден: ID {}", user.getId());
        }

        return user;
    }

    // Поиск пользователя по id
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // Сохрание user в БД
    public void updateUser(User user) {
        userRepository.save(user);
    }

    // Смена пароля
    public void changePassword(Long userId, String newPassword) {
        User user = findById(userId);
        if (user != null) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        }
    }

    // Метод для проверки пароля
    public boolean checkPassword(User user, String rawPassword) {

        String storedPassword = user.getPassword();

        if (!storedPassword.startsWith("$2a$")) {
        throw new RuntimeException("Пароль пользователя " + user.getUsername() + 
                                 " не был захэширован.");
    }

        return passwordEncoder.matches(rawPassword, user.getPassword());
    }
}

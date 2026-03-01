package com.notes.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.notes.app.repository.UserRepository;
import com.notes.app.model.User;
import java.time.LocalDateTime;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Регистрация пользователя
    public User register(String username, String password) {

        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();

        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    // Поиск пользователя по username
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
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

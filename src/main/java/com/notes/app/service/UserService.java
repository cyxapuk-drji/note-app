package com.notes.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.notes.app.repository.UserRepository;
import com.notes.app.model.User;
import java.time.LocalDateTime;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    // Регистрация пользователя
    public User register(String username, String password) {
        
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();

        user.setUsername(username);
        user.setPassword(password);
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
}

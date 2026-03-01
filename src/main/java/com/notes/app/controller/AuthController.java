package com.notes.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.notes.app.service.UserService;
import com.notes.app.model.User;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private UserService userService;

    // Страница входа
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // Отправка запроса на вход
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
    
        User user = userService.findByUsername(username);

        if (user != null && userService.checkPassword(user, password)) {
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());
            return "redirect:/notes";
        }
    
        model.addAttribute("error", "Неверный логин или пароль");
        return "login";
    }

    // Получение страницы с регистрацией
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    // Отправка запроса на регистрацию
    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password) {
        userService.register(username, password);
        return "redirect:/auth/login";
    }

    // Получить страницу со сменой пароля
    @GetMapping("/change-password")
    public String changePasswordPage() {
        return "change-password";
    }

    // Смена пароля
    @PostMapping("/change-password")
    public String changePassword(
        @RequestParam String oldPassword,
        @RequestParam String newPassword,
        @RequestParam String confirmPassword,
        HttpSession session,
        Model model) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/auth/login";
        }

        User currentUser = userService.findById(userId);
        
        if (!userService.checkPassword(currentUser, oldPassword)) {
            model.addAttribute("error", "Неверный старый пароль");
            return "change-password";
        }

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Новые пароли не совпадают");
            return "change-password";
        }

        if (oldPassword.equals(newPassword)) {
            model.addAttribute("error", "Новый пароль должен отличаться от старого");
            return "change-password";
        }

        if (newPassword.isEmpty()) {
            model.addAttribute("error", "Пароль не может быть пустым");
            return "change-password";
        }

        try {
            userService.changePassword(userId, newPassword);
            session.invalidate();
            model.addAttribute("message", "Пароль успешно изменен. Войдите с новым паролем.");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "change-password";
        }
        
        return "redirect:/auth/login";
    }

    // Выход
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login";
    }
}

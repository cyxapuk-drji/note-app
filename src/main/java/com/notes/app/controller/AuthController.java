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
    
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("userId", user.getId());
            System.out.println("LOGIN: userId=" + user.getId() + " sessionId=" + session.getId());
            return "notes";
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

    // Выход
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login";
    }
}

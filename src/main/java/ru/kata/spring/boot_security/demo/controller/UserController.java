package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginForm(Model model, String error) {
        if (error != null) {
            model.addAttribute("errorMessage", "Неверное имя пользователя или пароль");
        }
        return "index";
    }

    // вывод всех пользователей
    @GetMapping(value = "/admin")
    public String printUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    // страница добавления пользователя
    @GetMapping(value = "/add")
    public String addUserForm() {
        return "add";
    }

    // команда на форме добавления пользователя
    @PostMapping("/add")
    public String addUser(@ModelAttribute User user) {
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/edit/{id}")
    public String editUserForm(Model model, @PathVariable int id) {
        model.addAttribute("user", userService.getUserById(id));
        return "edit";
    }

    @PatchMapping(value = "/edit/{id}")
    public String editUser(@ModelAttribute User user, @PathVariable int id) {
        userService.changeUser(user, id);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }

    // вывод пользователя
    @GetMapping(value = "/user/{id}")
    public String printUser(Model model, @PathVariable int id) {
        model.addAttribute("user", userService.getUserById(id));
        return "user";
    }
}

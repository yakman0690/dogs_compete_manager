/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yakman.domain.User;
import ru.yakman.exception.UserAlreadyExistsException;
import ru.yakman.service.IEmailService;
import ru.yakman.service.IUserService;
import ru.yakman.utils.PasswordUtils;
import sun.security.util.Password;

/**
 *
 * @author Test
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IEmailService emailService;

    @GetMapping(value = "/current", produces = "application/json")
    public User getCurrentUser(@AuthenticationPrincipal Object principal) {
        return userService.getCurrentUser(principal);
    }

    /*@RequestMapping("/vkuser")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        
    }*/
    @PostMapping(value = "/save")
    public Map<String, Object> saveUser(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();
        try {
            userService.addUser(user);
        } catch (UserAlreadyExistsException ex) {
            result.put("result", false);
            result.put("message", "Пользователь с логином " + user.getLogin() + " уже зарегистрирован! ");
            return result;
        } catch (Exception ex) {
            result.put("result", false);
            result.put("message", "Неизвестная ошибка при добавлении нового пользователя");
            return result;
        }
        result.put("result", true);
        result.put("message", "Пользователь с логином " + user.getLogin() + " успешно зарегистрирован!");
        return result;
    }

    @PostMapping(value = "/update")
    public Map<String, Object> updateUser(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();
        try {
            userService.updateUser(user);
        } catch (Exception ex) {
            result.put("result", false);
            result.put("message", "Неизвестная ошибка при добавлении нового пользователя");
            return result;
        }
        result.put("result", true);
        result.put("message", "Личные данные пользователя " + user.getLogin() + " обновлены");
        return result;
    }

    @PostMapping(value = "/updatePassword")
    public Map<String, Object> updateUserPassword(@RequestBody Map<String, Object> data) {
        Map<String, Object> result = new HashMap<>();
        try {
            String login = (String) data.get("login");
            String password = (String) data.get("password");
            userService.updateUserPassword(login, password);
        } catch (Exception ex) {
            result.put("result", false);
            result.put("message", "Неизвестная ошибка при добавлении нового пользователя");
            return result;
        }
        result.put("result", true);
        result.put("message", "Личные данные пользователя обновлены");
        return result;
    }

    @GetMapping(value = "/getPassword")
    public Map<String, Object> updateUserPassword(@RequestParam String login) {
        Map<String, Object> result = new HashMap<>();
        try {
            String email = userService.getUserEmail(login);
            if (email == null) {
                result.put("result", false);
                result.put("message", "Вы не указали почтовый адрес для восстановления пароля. Для восстановления напишите нам письмо на yakman.test@gmail.com");
                return result;
            } else {
                String password = PasswordUtils.generatePassword();
                userService.updateUserPassword(login, password);
                emailService.send(email, "Ваш новый пароль от сервиса " + password);
                String emailAst = email.substring(0, 3) + "******" + email.substring(email.length() - 9, email.length());
                result.put("result", true);
                result.put("message", "Новый пароль выслан на Ваш почтовый ящик " + emailAst);
                return result;
            }

        } catch (Exception ex) {
            result.put("result", false);
            result.put("message", "Неизвестная ошибка при восстановлении пароля");
            return result;
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.yakman.domain.User;
import ru.yakman.exception.EntityAlreadyExistsException;
import ru.yakman.service.IEmailService;
import ru.yakman.service.IUserService;
import ru.yakman.service.IStorageService;

/**
 *
 * @author Test
 */
@RestController
@RequestMapping("/user")
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
        } catch (EntityAlreadyExistsException ex) {
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
            result.put("message", "Неизвестная ошибка при обновлении пароля пользователя");
            return result;
        }
        result.put("result", true);
        result.put("message", "Пароль пользователя обновлен");
        return result;
    }

    @Autowired
    private IStorageService storageService;

    @PostMapping("/upload")
    public void handleFileUpload(@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {
        storageService.store(file);
    }

}

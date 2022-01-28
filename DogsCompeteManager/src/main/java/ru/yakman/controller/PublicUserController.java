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
import org.springframework.web.bind.annotation.RestController;
import ru.yakman.domain.User;
import ru.yakman.exception.EntityAlreadyExistsException;
import ru.yakman.service.IUserService;

/**
 *
 * @author Test
 */

@RestController
@RequestMapping("/public/user")
public class PublicUserController {
    
    @Autowired
    private IUserService userService;
    
    @GetMapping(value = "/current", produces = "application/json")
    public User getCurrentUser(@AuthenticationPrincipal Object principal) {
        return userService.getCurrentUser(principal);
    }
    
     @PostMapping(value = "/add")
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

    
}

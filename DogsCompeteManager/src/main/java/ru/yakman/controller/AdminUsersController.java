/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yakman.domain.User;
import ru.yakman.service.IUserService;

/**
 *
 * @author Test
 */
@RestController
@RequestMapping("/admin/users")
public class AdminUsersController {

    @Autowired
    private IUserService userService;

    @GetMapping(value = "/all", produces = "application/json")
    public List<User> getAll() {
        return userService.getAll();
    }
    
    //TODO: check
    @PostMapping(value = "/updatePassword")
    public Map<String, Object> updateUserPassword(@RequestBody Map<String, Object> data) {
        Map<String, Object> result = new HashMap<>();
        try {
            String login = (String) data.get("login");
            String password = (String) data.get("password");
            userService.updateUserPassword(login, password);
        } catch (Exception ex) {
            result.put("result", false);
            result.put("message", "Неизвестная ошибка при изменении пароля пользователя");
            return result;
        }
        result.put("result", true);
        result.put("message", "Пароль пользователя обновлен");
        return result;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yakman.service.IEmailService;
import ru.yakman.service.IUserService;
import ru.yakman.utils.PasswordUtils;

/**
 *
 * @author Test
 */
@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IEmailService emailService;

    @GetMapping(value = "/restorePassword")
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

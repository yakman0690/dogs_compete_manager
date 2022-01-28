/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman.service;

import java.security.Principal;
import java.util.List;
import ru.yakman.domain.User;

/**
 *
 * @author Test
 */
public interface IUserService {

    public User getCurrentUser(Object principal);

    public List<User> getAll();

    public void addUser(User user) throws Exception;

    public void updateUser(User user) throws Exception;

    public void updateUserPassword(String login, String password) throws Exception;
    
    public String getUserEmail(String login) throws Exception;
    
    public void createServiceAdmin();

}

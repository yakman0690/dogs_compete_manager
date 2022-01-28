/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import ru.yakman.domain.PersonalData;
import ru.yakman.domain.User;
import ru.yakman.exception.EntityAlreadyExistsException;
import ru.yakman.exception.UserDoesntExistException;
import ru.yakman.repository.UserRepository;
import ru.yakman.service.IUserService;
import ru.yakman.utils.PasswordUtils;

/**
 *
 * @author Test
 */
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    private User getCurrentVKUser(DefaultOAuth2User user) {
        String vkId = user.getAttributes().get("id").toString();
        Optional<User> userOpt = userRepository.findByVkId(vkId);
        if (userOpt.isPresent()) {
            return userOpt.get();
        } else {
            User u = new User();
            u.setLogin("vk_" + vkId);
            String pw = "vk_" + vkId;
            u.setVkId(vkId);
            u.setPassword(PasswordUtils.encodePassword(pw));
            u.setRole("USER");
            PersonalData pd = new PersonalData();
            pd.setFirstName(user.getAttributes().get("first_name").toString());
            pd.setLastName(user.getAttributes().get("last_name").toString());
            pd.setImageUrl(user.getAttributes().get("photo_max").toString());
            u.setPersonalData(pd);
            userRepository.save(u);
            return u;
        }

    }

    private User getCurrentFormLoginUser(org.springframework.security.core.userdetails.User userDetails) {
        String login = userDetails.getUsername();
        Optional<User> userOpt = userRepository.findByLogin(login);
        if (userOpt.isPresent()) {
            return userOpt.get();
        }
        return null;
    }

    @Override
    public User getCurrentUser(Object principal) {
        if (principal instanceof org.springframework.security.core.userdetails.User) {
            return getCurrentFormLoginUser((org.springframework.security.core.userdetails.User) principal);
        } else if (principal instanceof DefaultOAuth2User) {
            return getCurrentVKUser((DefaultOAuth2User) principal);
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public void addUser(User user) throws Exception {
        Optional<User> existing = userRepository.findByLogin(user.getLogin());
        if (existing.isPresent()) {
            throw new EntityAlreadyExistsException();
        }

        String pw = user.getPassword();
        user.setPassword(PasswordUtils.encodePassword(pw));
        PersonalData pd = new PersonalData();
        pd.setFirstName("Новый пользователь");
        user.setPersonalData(pd);

        userRepository.save(user);

    }

    @Override
    public void updateUser(User user) throws Exception {
        Optional<User> existing = userRepository.findByLogin(user.getLogin());
        if (!existing.isPresent()) {
            throw new UserDoesntExistException();
        }
        userRepository.save(user);

    }

    @Override
    public void updateUserPassword(String login, String password) throws Exception {
        Optional<User> existing = userRepository.findByLogin(login);
        if (!existing.isPresent()) {
            throw new UserDoesntExistException();
        }
        User user = existing.get();
        user.setPassword(PasswordUtils.encodePassword(password));
        userRepository.save(user);

    }

    @Override
    public String getUserEmail(String login) throws Exception {
        Optional<User> existing = userRepository.findByLogin(login);
        if (!existing.isPresent()) {
            throw new UserDoesntExistException();
        }

        User user = existing.get();
        return user.getPersonalData().getEmail();
    }

    @Override
    public void createServiceAdmin() {
        Optional<User> adminOpt = userRepository.findByLogin("admin");
        if (!adminOpt.isPresent()) {
            User admin = new User();
            admin.setLogin("admin");
            admin.setPassword(PasswordUtils.encodePassword("admin"));
            admin.setRole("ADMIN");
            PersonalData pd = new PersonalData();
            pd.setFirstName("Администратор");
            admin.setPersonalData(pd);
            userRepository.save(admin);
        }
    }

}

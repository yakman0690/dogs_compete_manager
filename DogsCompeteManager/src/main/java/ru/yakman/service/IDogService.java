/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman.service;

import java.util.List;
import ru.yakman.domain.Dog;
import ru.yakman.domain.User;

/**
 *
 * @author Test
 */
public interface IDogService {

    public List<Dog> getAll();

    public List<Dog> findByUser(User user);

    public Dog findOne(Long id);

    public void deleteOne(Long id);

    public void addNewDog(Dog dog, User user) throws Exception;
}

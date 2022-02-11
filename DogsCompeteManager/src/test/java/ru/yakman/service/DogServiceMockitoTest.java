/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yakman.domain.Dog;
import ru.yakman.domain.User;
import ru.yakman.repository.DogRepository;
import ru.yakman.service.impl.DogServiceImpl;

/**
 *
 * @author Test
 */
@ExtendWith(MockitoExtension.class)
public class DogServiceMockitoTest {

    @Mock
    private DogRepository dogRepository;

    @InjectMocks
    private IDogService dogService = new DogServiceImpl();

    private List<Dog> dogs;

    void initDogsList() {
        dogs = new ArrayList<>();

        User user1 = new User();
        user1.setId(1l);
        user1.setLogin("login1");
        user1.setPassword("password1");
        Dog dog1 = new Dog();
        dog1.setBirthday(new Date());
        dog1.setBreed("breed 1");
        dog1.setGender(Dog.Gender.MALE);
        dog1.setId(1l);
        dog1.setName("name 1");
        dog1.setPedigree("pedigree 1");
        dog1.setTatoo("tatoo 1");
        dog1.setOwner(user1);
        Dog dog2 = new Dog();
        dog2.setBirthday(new Date());
        dog2.setBreed("breed 2");
        dog2.setGender(Dog.Gender.MALE);
        dog2.setId(2l);
        dog2.setName("name 2");
        dog2.setPedigree("pedigree 2");
        dog2.setTatoo("tatoo 2");
        dog2.setOwner(user1);
        Dog dog3 = new Dog();
        dog3.setBirthday(new Date());
        dog3.setBreed("breed 3");
        dog3.setGender(Dog.Gender.MALE);
        dog3.setId(3l);
        dog3.setName("name 3");
        dog3.setPedigree("pedigree 3");
        dog3.setTatoo("tatoo 3");
        dog3.setOwner(user1);

        User user2 = new User();
        user2.setId(2l);
        user2.setLogin("login2");
        user2.setPassword("password2");
        Dog dog4 = new Dog();
        dog4.setBirthday(new Date());
        dog4.setBreed("breed 4");
        dog4.setGender(Dog.Gender.MALE);
        dog4.setId(4l);
        dog4.setName("name 4");
        dog4.setPedigree("pedigree 4");
        dog4.setTatoo("tatoo 4");
        dog4.setOwner(user2);

        dogs.add(dog1);
        dogs.add(dog2);
        dogs.add(dog3);
        dogs.add(dog4);

    }

    @BeforeEach
    void setMockOutput() {
        initDogsList();
        Mockito.lenient().when(dogRepository.findAll()).thenReturn(dogs);
    }

    @DisplayName("Test get all")
    @Test
    void testGetAll() {
        Assertions.assertEquals(dogService.getAll(), dogs);
    }

    @DisplayName("Test add new")
    @Test
    void testAddNew() {
        try {
            User user3 = new User();
            user3.setId(3l);
            user3.setLogin("login3");
            user3.setPassword("password3");
            Dog dog99 = new Dog();
            dog99.setBirthday(new Date());
            dog99.setBreed("breed 99");
            dog99.setGender(Dog.Gender.MALE);
            dog99.setId(99l);
            dog99.setName("name 99");
            dog99.setPedigree("pedigree 99");
            dog99.setTatoo("tatoo 99");

            Mockito.lenient().when(dogRepository.findFirstByTatoo(Mockito.anyString())).thenReturn(Optional.empty());
            Mockito.lenient().when(dogRepository.findFirstByPedigree(Mockito.anyString())).thenReturn(Optional.empty());
            Mockito.lenient().when(dogRepository.save(dog99)).then((iom) -> {
                iom.getArgument(0);
                dogs.add(dog99);
                return dog99;
            });

            dogService.addNewDog(dog99, user3);
            Assertions.assertEquals(5, dogs.size());
        } catch (Exception ex) {
            Logger.getLogger(DogServiceMockitoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

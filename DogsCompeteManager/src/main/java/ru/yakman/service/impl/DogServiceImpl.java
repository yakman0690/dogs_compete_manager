/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yakman.domain.Dog;
import ru.yakman.domain.User;
import ru.yakman.exception.EntityAlreadyExistsException;
import ru.yakman.repository.DogRepository;
import ru.yakman.service.IDogService;

/**
 *
 * @author Test
 */
@Service
public class DogServiceImpl implements IDogService {

    @Autowired
    private DogRepository dogRepository;


    @Override
    public List<Dog> getAll() {
        return dogRepository.findAll();
    }

    @Override
    public List<Dog> findByUser(User user) {
        return dogRepository.findByOwner(user);
    }

    @Override
    public Dog findOne(Long id) {
        return dogRepository.getById(id);
    }

    @Override
    public void deleteOne(Long id) {
        dogRepository.deleteById(id);
    }

    @Override
    public void addNewDog(Dog dog, User user) throws Exception {
        if (dog.getTatoo() != null) {
            Optional tatooOpt = dogRepository.findFirstByTatoo(dog.getTatoo());
            if (tatooOpt.isPresent()) {
                throw new EntityAlreadyExistsException();
            }
        }
        if (dog.getPedigree() != null) {
            Optional pedigreeOpt = dogRepository.findFirstByPedigree(dog.getPedigree());
            if (pedigreeOpt.isPresent()) {
                throw new EntityAlreadyExistsException();
            }
        }
        dog.setOwner(user);
        dogRepository.save(dog);
    }

}

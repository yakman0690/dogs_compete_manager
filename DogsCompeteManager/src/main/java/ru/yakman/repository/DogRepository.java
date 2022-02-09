/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.yakman.domain.Dog;
import ru.yakman.domain.User;

/**
 *
 * @author Test
 */
public interface DogRepository extends JpaRepository<Dog, Long> {

    public List<Dog> findByOwner(User user);

    Optional<Dog> findFirstByTatoo(String tatoo);

    Optional<Dog> findFirstByPedigree(String pedigree);

}

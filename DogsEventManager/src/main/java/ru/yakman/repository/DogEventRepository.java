/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.yakman.domain.DogEvent;

/**
 *
 * @author Test
 */
public interface DogEventRepository extends MongoRepository<DogEvent, Long> {

}

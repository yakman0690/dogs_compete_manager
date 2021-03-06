/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.yakman.domain.DogEvent;

/**
 *
 * @author Test
 */
public interface DogEventRepository extends JpaRepository<DogEvent, Long>, JpaSpecificationExecutor<DogEvent>,
        QuerydslPredicateExecutor{

    public List<DogEvent> findByDate(Date date);
}

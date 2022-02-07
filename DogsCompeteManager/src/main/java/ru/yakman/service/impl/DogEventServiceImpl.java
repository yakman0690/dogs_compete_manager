/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman.service.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import javax.jms.JMSSessionMode;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.yakman.aop.SendJMSMessage;
import ru.yakman.domain.Dog;
import ru.yakman.domain.DogEvent;
import ru.yakman.domain.User;
import ru.yakman.repository.DogEventRepository;
import ru.yakman.service.IDogEventService;
import ru.yakman.service.IDogService;

/**
 *
 * @author Test
 */
@Service
public class DogEventServiceImpl implements IDogEventService {

    @Autowired
    private DogEventRepository dogEventRepository;

    @Autowired
    private IDogService dogService;

    @Override
    public List<DogEvent> getAll() {
        return dogEventRepository.findAll();
    }

    @Override
    public List<DogEvent> findByDate(Date date) {
        return dogEventRepository.findByDate(date);
    }

    @Override
    public DogEvent findOne(Long id) {
        return dogEventRepository.getById(id);
    }

    @Override
    @SendJMSMessage
    public void deleteOne(Long id) {
        dogEventRepository.deleteById(id);
    }

    @Override
    @SendJMSMessage
    public void addNewEvent(DogEvent dogEvent) {
        dogEventRepository.save(dogEvent);
    }

    @Override
    public List<DogEvent> getFuture() {
        return dogEventRepository.findAll(eventAfterDate());
    }

    public static Specification<DogEvent> eventAfterDate() {
        return new Specification<DogEvent>() {
            @Override
            public Predicate toPredicate(Root<DogEvent> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.greaterThan(root.get("date"), new Date());
            }
        };
    }

    public static Specification<DogEvent> eventWhereDogParticipate(Long id) {
        return new Specification<DogEvent>() {
            @Override
            public Predicate toPredicate(Root<DogEvent> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Join<Object, Object> jn = root.join("participants", JoinType.INNER);
                return criteriaBuilder.equal(jn.get("id"), id);
            }
        };
    }

    public List<DogEvent> getForDog(Dog dog) {
        return dogEventRepository.findAll(eventWhereDogParticipate(dog.getId()));
    }

    @Override
    public List<DogEvent> getForUser(User user) {
        List<Dog> dogs = dogService.findByUser(user);
        List<DogEvent> events = new LinkedList<>();
        dogs.forEach(dog -> {
            events.addAll(getForDog(dog));
        });
        return events.stream().distinct().collect(Collectors.toList());
    }

    @Override
    @SendJMSMessage
    public void signUpDog(Long eventId, Long dogId) {
        DogEvent de = dogEventRepository.getById(eventId);
        Dog dog = dogService.findOne(dogId);
        if (!de.getParticipants().contains(dog)) {
            de.getParticipants().add(dog);
        }
        dogEventRepository.save(de);

    }

}

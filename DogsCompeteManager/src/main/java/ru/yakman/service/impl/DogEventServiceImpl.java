/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yakman.domain.DogEvent;
import ru.yakman.repository.DogEventRepository;
import ru.yakman.service.IDogEventService;

/**
 *
 * @author Test
 */
@Service
public class DogEventServiceImpl implements IDogEventService {

    @Autowired
    private DogEventRepository dogEventRepository;

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
    public void deleteOne(Long id) {
        dogEventRepository.deleteById(id);
    }

    @Override
    public void addNewEvent(DogEvent dogEvent) {
        dogEventRepository.save(dogEvent);
    }

}

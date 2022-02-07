/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman.service.impl;

import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.yakman.domain.DogEvent;
import ru.yakman.repository.DogEventRepository;
import ru.yakman.service.EventGetService;

/**
 *
 * @author Test
 */
@Service
public class EventGetServiceImpl implements EventGetService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${remote.server.host}")
    private String host;
    @Value("${remote.server.port}")
    private int port;

    @Autowired
    private DogEventRepository repository;

    
    //naive implementation - delete and save all, for tests only
    @Override
    public void updateEventList() {
        repository.deleteAll();
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http");
        builder.setHost(host);
        builder.setPort(port);
        builder.setPath("/remote/events/all");
        try {
            String str = restTemplate.getForObject(builder.build(), String.class);
            DogEvent[] events = restTemplate.getForObject(builder.build(), DogEvent[].class);
            for (DogEvent e : events) {
                repository.save(e);
            }
        } catch (URISyntaxException ex) {
            Logger.getLogger(EventGetServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

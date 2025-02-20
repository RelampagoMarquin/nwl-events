package com.corellia.nlw.repositories;

import com.corellia.nlw.models.Event;

import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Integer> {
    public Event findByPrettyName(String prettyName);
    
}
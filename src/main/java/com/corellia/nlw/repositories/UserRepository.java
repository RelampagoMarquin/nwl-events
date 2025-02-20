package com.corellia.nlw.repositories;

import org.springframework.data.repository.CrudRepository;

import com.corellia.nlw.models.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    public User findByEmail(String email);  
}

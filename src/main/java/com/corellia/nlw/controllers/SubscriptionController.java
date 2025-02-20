package com.corellia.nlw.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.corellia.nlw.dtos.ErrorMessage;
import com.corellia.nlw.dtos.SubscriptionResponse;
import com.corellia.nlw.exceptions.EventNotFoundException;
import com.corellia.nlw.exceptions.SubscriptionConflictException;
import com.corellia.nlw.exceptions.UserIndicadorNotFoundException;
import com.corellia.nlw.models.User;
import com.corellia.nlw.services.SubscriptionService;

@RestController
public class SubscriptionController {

    @Autowired
    private SubscriptionService service;

    @PostMapping({"/subscription/{prettyName}", "/subscription/{prettyName}/{userId}"})
    public ResponseEntity<?> createSubscription(@PathVariable String prettyName, @RequestBody User subscriber, @PathVariable(required = false) Integer userId){
        try{
            SubscriptionResponse res = service.createNewSubscription(prettyName, subscriber, userId);
            if (res != null){
                return ResponseEntity.ok(res);
            }
            
        }catch(EventNotFoundException ex){
            return ResponseEntity.status(404).body(new ErrorMessage(ex.getMessage()));
        }
        catch(SubscriptionConflictException ex){
            return ResponseEntity.status(409).body(new ErrorMessage(ex.getMessage()));
        }
        catch(UserIndicadorNotFoundException ex){
            return ResponseEntity.status(404).body(new ErrorMessage(ex.getMessage()));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/subscription/{prettyName}/ranking")
    public ResponseEntity<?> generateRaningByEvent(@PathVariable String prettyName){
        try{
            return ResponseEntity.ok(service.getCompleteRanking(prettyName).subList(0, 3));
        }catch(EventNotFoundException e){
            return ResponseEntity.status(404).body(new ErrorMessage(e.getMessage()));
        }
    }

    @GetMapping("/subscription/{prettyName}/ranking/{userId}")
    public ResponseEntity<?> generateRaningByEventAndUser(@PathVariable String prettyName, @PathVariable Integer userId){
        try{
            return ResponseEntity.ok(service.getRankingByUser(prettyName, userId));
        }catch(Exception ex){
            return ResponseEntity.status(404).body(new ErrorMessage(ex.getMessage()));
        }
    } 
}

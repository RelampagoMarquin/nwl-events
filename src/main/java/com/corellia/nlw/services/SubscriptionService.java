package com.corellia.nlw.services;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.corellia.nlw.dtos.SubscriptionRankingByUser;
import com.corellia.nlw.dtos.SubscriptionRankingItem;
import com.corellia.nlw.dtos.SubscriptionResponse;
import com.corellia.nlw.exceptions.EventNotFoundException;
import com.corellia.nlw.exceptions.SubscriptionConflictException;
import com.corellia.nlw.exceptions.UserIndicadorNotFoundException;
import com.corellia.nlw.models.Event;
import com.corellia.nlw.models.Subscription;
import com.corellia.nlw.models.User;
import com.corellia.nlw.repositories.EventRepository;
import com.corellia.nlw.repositories.SubscriptionRepository;
import com.corellia.nlw.repositories.UserRepository;

@Service
public class SubscriptionService {
    
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public SubscriptionResponse createNewSubscription(String eventName, User user, Integer userId){
        Event evt = eventRepository.findByPrettyName(eventName);
        if (evt == null){
            throw new EventNotFoundException("Evento " + eventName+ " não existe");
        }
        User userRec = userRepository.findByEmail(user.getEmail());
        if(userRec == null){
            userRec = userRepository.save(user);
        }

        User indicador = null;
        if(userId != null){
        indicador = userRepository.findById(userId).orElse(null);
            if (indicador == null) {
                throw new UserIndicadorNotFoundException("Usuário " +userId+ " indicador não existe");
            }
        }

        Subscription subs = new Subscription();
        subs.setEvent(evt);
        subs.setSubscriber(userRec);
        subs.setIndication(indicador);

        Subscription tmpSub = subscriptionRepository.findByEventAndSubscriber(evt, userRec);
        if(tmpSub != null){
            throw new SubscriptionConflictException("Já existe incrição para o usuário " + userRec.getName() + " no evento " + evt.getTitle());
        }

        Subscription res = subscriptionRepository.save(subs);
        return new SubscriptionResponse(res.getSubscriptionNumber(), "http://codecraft.com/subscription/"+res.getEvent().getPrettyName()+"/"+res.getSubscriber().getId());
    }

    public List<SubscriptionRankingItem> getCompleteRanking(String prettyName){
        Event evt = eventRepository.findByPrettyName(prettyName);
        if (evt ==null){
            throw new EventNotFoundException("Ranking do evento " + prettyName + " não existe");
        }
        return subscriptionRepository.generateRanking(evt.getEventId());
    }

    public SubscriptionRankingByUser getRankingByUser(String prettyName, Integer userId){
        List<SubscriptionRankingItem> ranking = getCompleteRanking(prettyName);
        SubscriptionRankingItem item = ranking.stream().filter(i->i.userId().equals(userId)).findFirst().orElse(null);
        if(item == null){
            throw new UserIndicadorNotFoundException("Não há inscrições com indicação do usuário "+userId);
        }
        Integer posicao = IntStream.range(0, ranking.size())
                          .filter(pos -> ranking.get(pos).userId().equals(userId))
                          .findFirst().getAsInt();
        return new SubscriptionRankingByUser(item, posicao+1);
    }
}

package com.driver.services;


import com.driver.EntryDto.SubscriptionEntryDto;
import com.driver.model.Subscription;
import com.driver.model.SubscriptionType;
import com.driver.model.User;
import com.driver.repository.SubscriptionRepository;
import com.driver.repository.UserRepository;
import com.driver.trasformer.SubscriptionTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SubscriptionService {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    UserRepository userRepository;

    public Integer buySubscription(SubscriptionEntryDto subscriptionEntryDto){

        //Save The subscription Object into the Db and return the total Amount that user has to pay
        User user = userRepository.findById(subscriptionEntryDto.getUserId()).get();
        Subscription subscription = SubscriptionTransformer.convertDTOToEntity(subscriptionEntryDto);

        int totalAmt = 0;
        if(subscription.getSubscriptionType().equals(SubscriptionType.BASIC)){
            totalAmt = totalAmt + (500 + (200*subscription.getNoOfScreensSubscribed()));
        }
        else if(subscription.getSubscriptionType().equals(SubscriptionType.PRO)){
            totalAmt = totalAmt + (800 + (250*subscription.getNoOfScreensSubscribed()));
        }
        else {
            totalAmt = totalAmt + (1000 + (350*subscription.getNoOfScreensSubscribed()));
        }

        subscription.setTotalAmountPaid(totalAmt);
        subscription.setUser(user);
        Date purchaseDate= new Date();
        subscription.setStartSubscriptionDate(purchaseDate);
        user.setSubscription(subscription);
        subscriptionRepository.save(subscription);
        userRepository.save(user);
        return totalAmt;
    }

    public Integer upgradeSubscription(Integer userId)throws Exception{

        //If you are already at an ElITE subscription : then throw Exception ("Already the best Subscription")
        //In all other cases just try to upgrade the subscription and tell the difference of price that user has to pay
        //update the subscription in the repository
        User user = userRepository.findById(userId).get();
        Subscription subscription = user.getSubscription();
        SubscriptionType subscriptionType = subscription.getSubscriptionType();

        int prevAmt = subscription.getTotalAmountPaid();
        int currAmt = 0;

        if(subscriptionType.equals(SubscriptionType.ELITE)){
            throw new Exception("Already the best Subscription");
        }
        else if(subscriptionType.equals(SubscriptionType.BASIC)){
            currAmt = (300 + (50*subscription.getNoOfScreensSubscribed()));
        }
        else {
            currAmt = (200 + (100*subscription.getNoOfScreensSubscribed()));
        }

        int totalAmt = prevAmt+currAmt;
        subscription.setTotalAmountPaid(totalAmt);
        user.setSubscription(subscription);
        userRepository.save(user);
        subscriptionRepository.save(subscription);
        return currAmt;
    }

    public Integer calculateTotalRevenueOfHotstar(){

        //We need to find out total Revenue of hotstar : from all the subscriptions combined
        //Hint is to use findAll function from the SubscriptionDb
        int totalRevenue = 0;
        List<Subscription> subscriptionList = subscriptionRepository.findAll();
        for (Subscription subscription : subscriptionList){
            totalRevenue = totalRevenue + subscription.getTotalAmountPaid();
        }
        return totalRevenue;
    }

}

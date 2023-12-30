package com.driver.trasformer;

import com.driver.EntryDto.SubscriptionEntryDto;
import com.driver.model.Subscription;

public class SubscriptionTransformer {

    public static Subscription convertDTOToEntity(SubscriptionEntryDto subscriptionEntryDto){
        Subscription subscription = new Subscription();
        subscription.setSubscriptionType(subscriptionEntryDto.getSubscriptionType());
        subscription.setNoOfScreensSubscribed(subscriptionEntryDto.getNoOfScreensRequired());
        return subscription;
    }
}

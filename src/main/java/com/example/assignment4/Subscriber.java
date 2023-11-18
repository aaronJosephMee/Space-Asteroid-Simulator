package com.example.assignment4;

import java.util.List;

public interface Subscriber
{
    void receiveNotification(String channelName, List<SpaceObject> listOfSubscribers);

    //void receiveNotification(String channelName, List<?> listOfSubscribers);
}

package com.example.assignment4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PublishSubscribe
{
    private HashMap<String, ArrayList<Subscriber>> channels = new HashMap<>();

    public void createChannel(String key, ArrayList<Subscriber> subscribers)
    {
        channels.put(key, subscribers);
    }

    public void publishToChannel(String key, List<SpaceObject> listOfSubscribers)
    {
        ArrayList<Subscriber> subscribers = channels.get(key);
        for(Subscriber subscriber : subscribers)
        {
            subscriber.receiveNotification(key, listOfSubscribers);
        }
    }

}

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

    // Can this not have a spaceObjectList? This would need the View to have a
    // direct reference to the Model/iModel so that it gets the changes.

    // This currently doesn't work for iModel because we're passing a List of SpaceObjects,
    // and we need to notify the View of the worldRotation (which is a double)
    public void publishToChannel(String key)
    {
        ArrayList<Subscriber> subscribers = channels.get(key);
        for(Subscriber subscriber : subscribers)
        {
            subscriber.receiveNotification(key);
        }
    }

}

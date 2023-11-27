package com.example.assignment4;

import java.util.ArrayList;
import java.util.HashMap;

enum ChannelName {
    CREATE_ASTEROID,
    CREATE_STAR,
    WORLD_ROTATE,
    MOUSE_MOVED,
    DELETE
}

public class PublishSubscribe
{

    // we can use an ENUM instead of a string, to avoid making an error when typing
    private HashMap<ChannelName, ArrayList<Subscriber>> channels = new HashMap<>();

    public void createChannel(ChannelName key, ArrayList<Subscriber> subscribers)
    {
        channels.put(key, subscribers);
    }

    // Can this not have a spaceObjectList? This would need the View to have a
    // direct reference to the Model/iModel so that it gets the changes.

    // This currently doesn't work for iModel because we're passing a List of SpaceObjects,
    // and we need to notify the View of the worldRotation (which is a double)
    public void publishToChannel(ChannelName key)
    {
        ArrayList<Subscriber> subscribers = channels.get(key);
        for(Subscriber subscriber : subscribers)
        {
            subscriber.receiveNotification(key);
        }
    }

}

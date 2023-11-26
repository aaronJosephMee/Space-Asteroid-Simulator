package com.example.assignment4;

public class InteractionModel
{
    private PublishSubscribe publisher;
    private double worldRotation = 0;

    public void setPublisher(PublishSubscribe publisher)
    {
        this.publisher = publisher;
    }

    public double getWorldRotation()
    {
        return worldRotation;
    }

    public void incrementWorldRotation(double incr)
    {
        this.worldRotation += incr;
        publisher.publishToChannel(ChannelName.WORLD_ROTATE);
    }
}

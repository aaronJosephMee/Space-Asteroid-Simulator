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

    public void setWorldRotation(double worldRotation)
    {
        this.worldRotation = worldRotation;
    }
}

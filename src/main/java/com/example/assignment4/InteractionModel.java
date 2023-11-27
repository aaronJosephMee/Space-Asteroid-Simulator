package com.example.assignment4;

public class InteractionModel
{
    private PublishSubscribe publisher;
    private double worldRotation = 0;
    private double mouseX, mouseY;

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

    public void setMouseCoords(double mouseX, double mouseY)
    {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        publisher.publishToChannel(ChannelName.MOUSE_MOVED);
    }
    public double getMouseX()
    {
        return mouseX;
    }
    public double getMouseY()
    {
        return mouseY;
    }
}

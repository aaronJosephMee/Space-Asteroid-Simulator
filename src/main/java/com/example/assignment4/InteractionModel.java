package com.example.assignment4;

public class InteractionModel
{
    private PublishSubscribe publisher;
    private double rotationSpeed = 0.01;
    private double currentRotation = 0.0;
    private double mouseX, mouseY;

    public void setPublisher(PublishSubscribe publisher)
    {
        this.publisher = publisher;
    }

    public double getRotationSpeed()
    {
        return rotationSpeed;
    }

    public void setRotationSpeed(double speed)
    {
        this.rotationSpeed = speed;
        //TODO uncomment this for the world rotation
        //publisher.publishToChannel(ChannelName.WORLD_ROTATE);
    }

    public double getCurrentRotation()
    {
        return currentRotation;
    }

    //TODO delete this
//    public void setCurrentRotation(double currentRotation)
//    {
//        this.currentRotation = currentRotation;
//    }

    public void rotateWorld()
    {
        currentRotation += rotationSpeed;
        //TODO uncomment this for the world rotation
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

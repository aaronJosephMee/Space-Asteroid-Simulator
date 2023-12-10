package com.example.assignment4;

import java.util.ArrayList;
import java.util.List;

public class InteractionModel
{
    private PublishSubscribe publisher;
    private double rotationSpeed = 0.01;
    private double currentRotation = 0.0;
    private double areaCursorSize = 0.0;
    private double mouseX, mouseY;
    private final ArrayList<Asteroid> selectedAsteroids = new ArrayList<>();

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
        //publisher.publishToChannel(ChannelName.WORLD_ROTATE);
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

    public double getAreaCursorSize()
    {
        return areaCursorSize;
    }

    public void addAsteroidToSelection(Asteroid asteroid)
    {
        asteroid.setVelocity(0, 0);
        asteroid.setSelected(true);
        selectedAsteroids.add(asteroid);
        publisher.publishToChannel(ChannelName.CREATE_ASTEROID);
    }

    public void removeAsteroidFromSelection(Asteroid asteroid)
    {
        asteroid.restoreOriginalVelocity();
        asteroid.setSelected(false);
        selectedAsteroids.remove(asteroid);
        publisher.publishToChannel(ChannelName.CREATE_ASTEROID);
    }

    public List<Asteroid> getSelectedAsteroids()
    {
        return selectedAsteroids;
    }

    public void modifyCursorSize(double cursorSizeDelta)
    {

        areaCursorSize += cursorSizeDelta;
        // TODO change this when the size gets scaled
        if(areaCursorSize < 0)
        {
            areaCursorSize = 0;
        }
        else if (areaCursorSize > 200)
        {
            areaCursorSize = 200;
        }


        //TODO change this channel
        publisher.publishToChannel(ChannelName.CREATE_ASTEROID);
    }
}

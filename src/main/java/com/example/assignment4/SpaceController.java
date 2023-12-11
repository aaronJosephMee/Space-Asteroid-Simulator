package com.example.assignment4;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class SpaceController
{
    private SpaceModel spaceModel;
    private InteractionModel iModel;
    private double initialX = 0;
    private double initialY = 0;
    private double dX = 0;
    private double dY = 0;

    private enum InteractionState
    {
        READY, DRAGGING
    }

    private InteractionState state = InteractionState.READY;

    public void handleAnimationTick()
    {
        spaceModel.moveAsteroids();
        spaceModel.spinAsteroids();
        iModel.rotateWorld();
    }

    public void handleMouseMoved(MouseEvent event, double canvasSize)
    {
        iModel.setMouseCoords(event.getX() / canvasSize, event.getY() / canvasSize);
    }

    public void handleMousePressed(MouseEvent event, int canvasSize)
    {
        Asteroid asteroid = spaceModel.getAsteroidAtCoords(event.getX() / canvasSize, event.getY() / canvasSize);
        if(asteroid != null)
        {
            if(asteroid.isSelected())
            {
                iModel.removeAsteroidFromSelection(asteroid);
            } else
            {
                iModel.addAsteroidToSelection(asteroid);
            }
        }
    }

    public void handleMouseDragged(MouseEvent event, int canvasSize)
    {
        if(state == InteractionState.READY)
        {
            state = InteractionState.DRAGGING;
            initialX = event.getX() / canvasSize;
            initialY = event.getY() / canvasSize;
            dX = initialX;
            dY = initialY;
        }
        else {
            double currentX = event.getX() / canvasSize;
            double currentY = event.getY() / canvasSize;
            spaceModel.dragAsteroids(iModel.getSelectedAsteroids(), currentX - dX, currentY - dY);

            dX = currentX;
            dY = currentY;
        }

    }

    public void handleMouseReleased(MouseEvent event, int canvasSize)
    {
        if(state == InteractionState.DRAGGING)
        {
            double finalX = ((event.getX() / canvasSize) - initialX) * 0.01;
            double finalY = ((event.getY() / canvasSize) - initialY) * 0.01;
            spaceModel.setAsteroidsVelocity(iModel.getSelectedAsteroids(), finalX, finalY);
            initialX = 0;
            initialY = 0;
            dX = 0;
            dY = 0;
            state = InteractionState.READY;
        }
    }

    public void handleWheel(ScrollEvent event, int canvasSize)
    {
        iModel.modifyCursorSize(event.getDeltaY() / canvasSize);
    }

    public void setSpaceModel(SpaceModel spaceModel)
    {
        this.spaceModel = spaceModel;
    }

    public void setiModel(InteractionModel iModel)
    {
        this.iModel = iModel;
    }
}

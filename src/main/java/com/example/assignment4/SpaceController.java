package com.example.assignment4;

import javafx.scene.input.MouseEvent;

public class SpaceController
{
    private SpaceModel spaceModel;
    private InteractionModel iModel;

    public void handleAnimationTick()
    {
        spaceModel.moveAsteroids();
        spaceModel.spinAsteroids();
    }

    public void handleMouseMoved(MouseEvent event)
    {
        iModel.setMouseCoords(event.getX(), event.getY());
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

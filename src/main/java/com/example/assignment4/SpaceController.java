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
        iModel.rotateWorld();
    }

    public void handleMouseMoved(MouseEvent event, double canvasSize)
    {
        iModel.setMouseCoords(event.getX() /canvasSize, event.getY() / canvasSize);
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

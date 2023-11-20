package com.example.assignment4;

public class SpaceController
{
    private SpaceModel spaceModel;
    private InteractionModel iModel;

    public void handleAnimationTick()
    {
        spaceModel.moveAsteroids();
        spaceModel.spinAsteroids();
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

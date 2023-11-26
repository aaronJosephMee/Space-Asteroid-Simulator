package com.example.assignment4;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class MainUI extends StackPane
{
    public MainUI()
    {
        // Add MVC components
        SpaceController spaceController = new SpaceController();
        SpaceModel spaceModel = new SpaceModel();
        InteractionModel iModel = new InteractionModel();
        SpaceView spaceView = new SpaceView(850);
        PublishSubscribe publishSubscribe = new PublishSubscribe();

        // Link them together
        spaceController.setSpaceModel(spaceModel);
        spaceController.setiModel(iModel);

        spaceView.setSpaceModel(spaceModel);
        spaceView.setiModel(iModel);

        // Add PublishSubscribe implementation:
        ArrayList<Subscriber> subscribersForCreate = new ArrayList<>();
        ArrayList<Subscriber> subscribersForDelete = new ArrayList<>();
        // Add the SpaceView to each list:
        subscribersForCreate.add(spaceView);
        subscribersForDelete.add(spaceView);

        publishSubscribe.createChannel(ChannelName.CREATE_ASTEROID, subscribersForCreate);
        publishSubscribe.createChannel(ChannelName.CREATE_STAR, subscribersForCreate);
        //TODO change the subscribersForCreate
        publishSubscribe.createChannel(ChannelName.WORLD_ROTATE, subscribersForCreate);
        publishSubscribe.createChannel(ChannelName.DELETE, subscribersForDelete);

        spaceModel.setPublisher(publishSubscribe);
        iModel.setPublisher(publishSubscribe);

        // Create 10 asteroids using createAsteroid() method
        for (int i = 0; i < 2; i++)
        {
            spaceModel.createAsteroid();
        }

        this.getChildren().add(spaceView);

        AnimationTimer animationTimer = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                spaceController.handleAnimationTick();
                iModel.incrementWorldRotation(0.01);
            }
        };

        animationTimer.start();
    }

}

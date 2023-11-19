package com.example.assignment4;

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
        SpaceView spaceView = new SpaceView(1000);
        PublishSubscribe publishSubscribe = new PublishSubscribe();

        // Add PublishSubscribe implementation:
        ArrayList<Subscriber> subscribersForCreate = new ArrayList<>();
        ArrayList<Subscriber> subscribersForDelete = new ArrayList<>();
        // Add the SpaceView to each list:
        subscribersForCreate.add(spaceView);
        subscribersForDelete.add(spaceView);

        publishSubscribe.createChannel("create-asteroid", subscribersForCreate);
        publishSubscribe.createChannel("create-star", subscribersForCreate);
        publishSubscribe.createChannel("delete", subscribersForDelete);

        spaceModel.setPublisher(publishSubscribe);
        iModel.setPublisher(publishSubscribe);

        // Create 10 asteroids using createAsteroid() method
        for (int i = 0; i < 10; i++)
        {
            spaceModel.createAsteroid();
        }

        this.getChildren().add(spaceView);
    }

}

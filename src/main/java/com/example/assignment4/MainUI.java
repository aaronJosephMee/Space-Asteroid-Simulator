package com.example.assignment4;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class MainUI extends GridPane
{
    public MainUI()
    {
        // Add MVC components
        SpaceController spaceController = new SpaceController();
        SpaceModel spaceModel = new SpaceModel();
        InteractionModel iModel = new InteractionModel();
        SpaceView spaceView = new SpaceView(850);
        SpaceView miniatureView = new SpaceView(150);
        PublishSubscribe publishSubscribe = new PublishSubscribe();

        // Link them together
        spaceController.setSpaceModel(spaceModel);
        spaceController.setiModel(iModel);

        spaceView.setSpaceModel(spaceModel);
        spaceView.setiModel(iModel);
        miniatureView.setSpaceModel(spaceModel);
        miniatureView.setiModel(iModel);

        // Add PublishSubscribe implementation:
        ArrayList<Subscriber> subscribersForCreate = new ArrayList<>();
        ArrayList<Subscriber> subscribersForDelete = new ArrayList<>();

        // Add the SpaceViews to each list:
        subscribersForCreate.add(spaceView);
        subscribersForDelete.add(spaceView);
        subscribersForCreate.add(miniatureView);
        subscribersForDelete.add(miniatureView);

        publishSubscribe.createChannel(ChannelName.CREATE_ASTEROID, subscribersForCreate);
        publishSubscribe.createChannel(ChannelName.CREATE_STAR, subscribersForCreate);
        //TODO change the subscribersForCreate
        publishSubscribe.createChannel(ChannelName.WORLD_ROTATE, subscribersForCreate);
        publishSubscribe.createChannel(ChannelName.DELETE, subscribersForDelete);

        spaceModel.setPublisher(publishSubscribe);
        iModel.setPublisher(publishSubscribe);

        // Create 10 asteroids using createAsteroid() method
        for (int i = 0; i < 10; i++)
        {
            spaceModel.createAsteroid();
        }

        VBox sidePanel = new VBox();
        sidePanel.setStyle("-fx-base: #191919; -fx-background-color: #191919");
        sidePanel.getChildren().add(miniatureView);

        this.add(sidePanel, 0, 0);
        this.add(spaceView, 1, 0);

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

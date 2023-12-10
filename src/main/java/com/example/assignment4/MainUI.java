package com.example.assignment4;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;

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
        CursorView cursorView = new CursorView(150);
        ControlPanelView controlPanelView = new ControlPanelView();
        PublishSubscribe pubSub = new PublishSubscribe();

        // Link them together
        spaceController.setSpaceModel(spaceModel);
        spaceController.setiModel(iModel);

        spaceView.setSpaceModel(spaceModel);
        spaceView.setiModel(iModel);
        spaceView.setupEvents(spaceController);
        miniatureView.setSpaceModel(spaceModel);
        miniatureView.setiModel(iModel);
        miniatureView.setupEvents(spaceController);
        cursorView.setSpaceModel(spaceModel);
        cursorView.setiModel(iModel);
        controlPanelView.setSpaceModel(spaceModel);
        controlPanelView.setiModel(iModel);

        // Add PublishSubscribe implementation:
        ArrayList<Subscriber> subscribersForCreate = new ArrayList<>();
        ArrayList<Subscriber> subscribersForWorldRotate = new ArrayList<>();
        ArrayList<Subscriber> subscribersForMouseMoved = new ArrayList<>();

        // Add the SpaceViews to each list:
        subscribersForCreate.addAll(Arrays.asList(spaceView, miniatureView, cursorView));
        subscribersForWorldRotate.addAll(Arrays.asList(spaceView, miniatureView, controlPanelView));
        subscribersForMouseMoved.add(cursorView);


        // Create the channels and add the subscribers for each channel
        pubSub.createChannel(ChannelName.CREATE_ASTEROID, subscribersForCreate);
        pubSub.createChannel(ChannelName.CREATE_STAR, subscribersForCreate);
        pubSub.createChannel(ChannelName.WORLD_ROTATE, subscribersForWorldRotate);
        pubSub.createChannel(ChannelName.MOUSE_MOVED, subscribersForMouseMoved);

        spaceModel.setPublisher(pubSub);
        iModel.setPublisher(pubSub);

        // Create 10 asteroids using createAsteroid() method
        for (int i = 0; i < 10; i++)
        {
            spaceModel.createAsteroid(850);
        }

        VBox sidePanel = new VBox();
        sidePanel.setStyle("-fx-base: #191919; -fx-background-color: #191919");
        sidePanel.getChildren().addAll(miniatureView, cursorView, controlPanelView);
        sidePanel.setSpacing(5);

        this.add(sidePanel, 0, 0);
        this.add(spaceView, 1, 0);

        AnimationTimer animationTimer = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                spaceController.handleAnimationTick();
            }
        };

        animationTimer.start();
    }

}

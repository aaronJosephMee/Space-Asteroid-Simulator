package com.example.assignment4;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ControlPanelView extends StackPane implements Subscriber
{
    SpaceModel spaceModel;
    InteractionModel iModel;
    private double rotationSpeed = 0;
    private boolean areAsteroidsMoving = true;
    private boolean areAsteroidsSpinning = true;
    public ControlPanelView()
    {
        VBox vbox = new VBox();
        Label label = new Label("Rotation Speed");
        Slider slider = new Slider(0.0, 0.1, rotationSpeed);
        slider.valueProperty().addListener((observable, oldValue, newValue) ->
            iModel.setRotationSpeed((Double) newValue)
        );

        HBox movementHbox = new HBox();
        CheckBox movementCheckbox = new CheckBox();
        movementCheckbox.setSelected(areAsteroidsMoving);
        movementCheckbox.selectedProperty().addListener((observable, oldValue, newValue) ->
            spaceModel.setAsteroidsMoving(newValue)
        );
        Label movementLabel = new Label("Asteroid Movement");
        movementHbox.getChildren().addAll(movementCheckbox, movementLabel);
        movementHbox.setSpacing(10);


        HBox spinHbox = new HBox();
        CheckBox spinCheckbox = new CheckBox();
        spinCheckbox.setSelected(areAsteroidsSpinning);
        spinCheckbox.selectedProperty().addListener((observable, oldValue, newValue) ->
            spaceModel.setAsteroidsSpinning(newValue)
        );
        Label spinLabel = new Label("Asteroid Spin");
        spinHbox.getChildren().addAll(spinCheckbox, spinLabel);
        spinHbox.setSpacing(10);

        vbox.setSpacing(10);
        vbox.getChildren().addAll(label, slider, movementHbox, spinHbox);
        this.getChildren().add(vbox);
    }

    public void setSpaceModel(SpaceModel spaceModel)
    {
        this.spaceModel = spaceModel;
        areAsteroidsMoving = spaceModel.areAsteroidsMoving();
        areAsteroidsSpinning = spaceModel.areAsteroidsSpinning();
    }

    public void setiModel(InteractionModel iModel)
    {
        this.iModel = iModel;
    }

    @Override
    public void receiveNotification(ChannelName channelName)
    {
        if(channelName == ChannelName.WORLD_ROTATE)
        {
            rotationSpeed = iModel.getRotationSpeed();
        }
    }
}

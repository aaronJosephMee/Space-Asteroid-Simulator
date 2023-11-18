package com.example.assignment4;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class SpaceView extends StackPane implements Subscriber
{
    private final int canvasSize;
    Canvas canvas;
    GraphicsContext gc;
    List<SpaceObject> stars = new ArrayList<>();
    List<SpaceObject> asteroids = new ArrayList<>();

    public SpaceView(int canvasSize)
    {
        this.canvasSize = canvasSize;

        this.canvas = new Canvas(canvasSize, canvasSize);
        this.gc = canvas.getGraphicsContext2D();
        this.getChildren().add(canvas);
    }

    // TODO
    public void drawOuterSpace()
    {
        // Clear canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Draw outer space
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Draw white dots for stars
        gc.setFill(Color.WHITE);
        for (SpaceObject star: stars)
        {
            double translated_x = star.getNormalizedX() * canvasSize;
            double translated_y = star.getNormalizedY() * canvasSize;
            gc.fillOval(translated_x, translated_y, 2 * star.getRadius(), 2 * star.getRadius());
        }

        // Draw asteroids
        gc.setFill(Color.DARKGRAY);
        for (SpaceObject asteroid: asteroids)
        {
            double translated_x = asteroid.getNormalizedX() * canvasSize;
            double translated_y = asteroid.getNormalizedY() * canvasSize;
            // did not translate radius as that does not deal with location?
            gc.fillOval(translated_x - asteroid.getRadius(),
                    translated_y - asteroid.getRadius(), 2.0 * asteroid.getRadius(),
                    2.0 * asteroid.getRadius());
        }

    }

    @Override
    public void receiveNotification(String channelName, List<SpaceObject> listOfSubscribers)
    {
        if(channelName.equals("create-star"))
        {
            this.stars = listOfSubscribers;
        }
        else if(channelName.equals("create-asteroid"))
        {
            this.asteroids = listOfSubscribers;
        }
        drawOuterSpace();
    }


}

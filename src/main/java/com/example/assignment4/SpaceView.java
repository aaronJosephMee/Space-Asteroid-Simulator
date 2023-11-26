package com.example.assignment4;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class SpaceView extends StackPane implements Subscriber
{
    private final int canvasSize;
    Canvas canvas;
    GraphicsContext gc;
    SpaceModel spaceModel;
    InteractionModel iModel;
    List<SpaceObject> stars = new ArrayList<>();
    List<SpaceObject> asteroids = new ArrayList<>();
    double worldRotation = 0;

    public SpaceView(int canvasSize)
    {
        this.canvasSize = canvasSize;

        this.canvas = new Canvas(canvasSize, canvasSize);
        this.gc = canvas.getGraphicsContext2D();
        this.getChildren().add(canvas);
    }

    public void drawOuterSpace()
    {
        // Clear canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Draw outer space
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Rotate depending on world rotation
        gc.save();
        gc.translate((double) canvasSize / 2, (double) canvasSize / 2); // Translate to the center
        gc.rotate(worldRotation); // Rotate
        gc.translate((double) -canvasSize / 2, (double) -canvasSize / 2);

        // Draw white dots for stars
        gc.setFill(Color.WHITE);
        for (SpaceObject star: stars)
        {
            double translated_x = star.getNormalizedX() * canvasSize;
            double translated_y = star.getNormalizedY() * canvasSize;
            gc.fillOval(translated_x, translated_y, 2 * star.getRadius(), 2 * star.getRadius());
        }

        // Draw asteroids
        for (SpaceObject asteroid: asteroids)
        {
            if(asteroid instanceof Asteroid)
            {
                gc.save();
                gc.setFill(Color.DARKGRAY);

                Asteroid tc_asteroid = (Asteroid) asteroid;
                System.out.println("Asteroid: " + tc_asteroid + '\n');

                tc_asteroid.setXTranslation(tc_asteroid.getNormalizedX() * canvasSize);
                tc_asteroid.setYTranslation(tc_asteroid.getNormalizedY() * canvasSize);

                System.out.println("normalized x: " + tc_asteroid.getNormalizedX());
                System.out.println("normalized y: " + tc_asteroid.getNormalizedY());
                System.out.println("translated x: " + tc_asteroid.getXTranslation());
                System.out.println("translated y: " + tc_asteroid.getYTranslation());
                System.out.println("angle: " + tc_asteroid.getAngle());

                //TODO delete here + delete count too
                gc.setFill(Color.RED);
                gc.setFont(new Font(15));
                gc.fillText(Integer.toString(tc_asteroid.myIndex), tc_asteroid.getXTranslation() - tc_asteroid.getRadius(),
                        tc_asteroid.getYTranslation() - tc_asteroid.getRadius());
                //TODO delete above here

                gc.translate(tc_asteroid.getXTranslation(), tc_asteroid.getYTranslation());
                gc.rotate(tc_asteroid.getAngle());
                gc.setStroke(Color.RED);
                gc.strokePolygon(tc_asteroid.getxPoints(), tc_asteroid.getyPoints(),
                        tc_asteroid.getxPoints().length);
                gc.setFill(Color.DARKGRAY);
                gc.fillPolygon(tc_asteroid.getxPoints(), tc_asteroid.getyPoints(),
                        tc_asteroid.getxPoints().length);
                gc.restore();
            }
        }
        gc.restore();
    }

    public void setSpaceModel(SpaceModel spaceModel)
    {
        this.spaceModel = spaceModel;
    }

    public void setiModel(InteractionModel iModel)
    {
        this.iModel = iModel;
    }

    public void receiveNotification(ChannelName channelName)
    {
        if(channelName == ChannelName.CREATE_STAR)
        {
            this.stars = spaceModel.getStars();
        }
        else if(channelName == ChannelName.CREATE_ASTEROID)
        {
            this.asteroids = spaceModel.getAsteroidList();
        }
        else if(channelName == ChannelName.WORLD_ROTATE)
        {
            //TODO update world rotation
            this.worldRotation = iModel.getWorldRotation();
        }
        drawOuterSpace();
    }
}

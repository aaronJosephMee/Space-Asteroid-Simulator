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
    List<SpaceObject> stars = new ArrayList<>();
    List<SpaceObject> asteroids = new ArrayList<>();

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
    }

    public void receiveNotification(String channelName, List<SpaceObject> spaceObjectsList)
    {
        if(channelName.equals("create-star"))
        {
            this.stars = spaceObjectsList;
        }
        else if(channelName.equals("create-asteroid"))
        {
            this.asteroids = spaceObjectsList;
        }
        drawOuterSpace();
    }
}

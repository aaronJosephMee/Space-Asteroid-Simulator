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
//        gc.save();

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
            gc.setFill(Color.DARKGRAY);
            double translated_x = asteroid.getNormalizedX() * canvasSize;
            double translated_y = asteroid.getNormalizedY() * canvasSize;

            System.out.println("translated x: " + translated_x);
            System.out.println("translated y: " + translated_y);

            gc.fillOval(translated_x - asteroid.getRadius(),
                    translated_y - asteroid.getRadius(), 2.0 * asteroid.getRadius(),
                    2.0 * asteroid.getRadius());

            //TODO delete here + delete count too


            if(asteroid instanceof Asteroid)
            {
                Asteroid typeCastAsteroid = (Asteroid) asteroid;
                System.out.println("Asteroid: " + typeCastAsteroid + '\n');

                gc.setFill(Color.RED);
                gc.setFont(new Font(15));
                gc.fillText(Integer.toString(typeCastAsteroid.myIndex), translated_x - typeCastAsteroid.getRadius(),
                        translated_y - typeCastAsteroid.getRadius());

//                gc.translate(translated_x, translated_y);
//                gc.setStroke(Color.RED);
//                gc.strokePolygon(typeCastAsteroid.getxPoints(), typeCastAsteroid.getyPoints(),
//                        typeCastAsteroid.getxPoints().length);
//                gc.fillPolygon(typeCastAsteroid.getxPoints(), typeCastAsteroid.getyPoints(),
//                        typeCastAsteroid.getxPoints().length);
//                gc.restore();
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

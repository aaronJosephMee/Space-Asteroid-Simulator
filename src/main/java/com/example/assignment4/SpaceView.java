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
    final int canvasSize;
    Canvas canvas;
    GraphicsContext gc;
    SpaceModel spaceModel;
    InteractionModel iModel;
    List<Star> stars = new ArrayList<>();
    List<Asteroid> asteroids = new ArrayList<>();
    double mouseX = 0;
    double mouseY = 0;
    double cursorSize = 0;
    double currentRotationAngle = 0;

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

        gc.setStroke(Color.WHITE);
        gc.strokeRect(0,0, canvas.getWidth(), canvas.getHeight());

        // Rotate depending on world rotation
        gc.save();
        gc.translate((double) canvasSize / 2, (double) canvasSize / 2); // Translate to the center
        gc.rotate(currentRotationAngle); // Rotate
        gc.translate((double) -canvasSize / 2, (double) -canvasSize / 2);

        // Draw white dots for stars
        gc.setFill(Color.WHITE);
        for (Star star: stars)
        {
            star.setTranslatedX(star.getNormalizedX() * canvasSize);
            star.setTranslatedY(star.getNormalizedY() * canvasSize);
            star.setTranslatedRadius(star.getNormalizedRadius() * ((double) canvasSize / 400));
            gc.fillOval(star.getTranslatedX(), star.getTranslatedY(), star.getTranslatedRadius(), star.getTranslatedRadius());
        }

        // Draw asteroids
        for (Asteroid asteroid: asteroids)
        {
            gc.save();
            //System.out.println("Asteroid: " + asteroid + '\n');

            asteroid.setTranslatedX(asteroid.getNormalizedX() * canvasSize);
            asteroid.setTranslatedY(asteroid.getNormalizedY() * canvasSize);
            asteroid.setTranslatedRadius(asteroid.getNormalizedRadius() *  canvasSize);

//            System.out.println("normalized x: " + asteroid.getNormalizedX());
//            System.out.println("normalized y: " + asteroid.getNormalizedY());
//            System.out.println("translated x: " + asteroid.getTranslatedX());
//            System.out.println("translated y: " + asteroid.getTranslatedY());
//            System.out.println("angle: " + asteroid.getAngle());

            //TODO delete here + delete count too
            gc.setFill(Color.RED);
            gc.setFont(new Font(15));
            gc.fillText(Integer.toString(asteroid.myIndex), asteroid.getTranslatedX() - asteroid.getTranslatedRadius(),
                    asteroid.getTranslatedY() - asteroid.getTranslatedRadius());
            //TODO delete above here

            gc.translate(asteroid.getTranslatedX(), asteroid.getTranslatedY());
            gc.rotate(asteroid.getAngle());
            gc.setStroke(Color.RED);
            gc.strokePolygon(asteroid.getxPoints(), asteroid.getyPoints(),
                    asteroid.getxPoints().length);
            gc.setFill(asteroid.isSelected() ? Color.YELLOW : Color.DARKGRAY);
            gc.fillPolygon(asteroid.getxPoints(), asteroid.getyPoints(),
                    asteroid.getxPoints().length);
            gc.restore();
        }
        gc.restore();

        gc.setFill(Color.GRAY.deriveColor(0, 1, 1, 0.5));
        //TODO change this for P/S when imodel changes, and normalize coords for cursorSize
        gc.fillOval((mouseX * canvasSize) - ((cursorSize * canvasSize) / 2),
                (mouseY * canvasSize) - ((cursorSize * canvasSize) / 2),
                cursorSize * canvasSize, cursorSize * canvasSize);
    }

    public void drawMouseArea()
    {

    }

    public void setSpaceModel(SpaceModel spaceModel)
    {
        this.spaceModel = spaceModel;
    }

    public void setiModel(InteractionModel iModel)
    {
        this.iModel = iModel;
    }

    public void setupEvents(SpaceController controller)
    {
        this.setOnMouseMoved(e -> controller.handleMouseMoved(e, canvasSize));
        this.setOnMouseDragged(e -> controller.handleMouseDragged(e, canvasSize));
        this.setOnMouseClicked(e -> controller.handleMousePressed(e, canvasSize));
        this.setOnMouseReleased(e -> controller.handleMouseReleased(e, canvasSize));
        this.setOnScroll(e -> controller.handleWheel(e, canvasSize));
    }
    public void receiveNotification(ChannelName channelName)
    {
        if (channelName == ChannelName.CREATE_STAR)
        {
            this.stars = spaceModel.getStars();
        } else if (channelName == ChannelName.CREATE_ASTEROID)
        {
            this.asteroids = spaceModel.getAsteroidList();
        } else if (channelName == ChannelName.WORLD_ROTATE)
        {
            //TODO update world rotation
            this.currentRotationAngle = iModel.getCurrentRotation();
        } else if (channelName == ChannelName.AREA_CURSOR)
        {
            this.mouseX = iModel.getMouseX();
            this.mouseY = iModel.getMouseY();
            this.cursorSize = iModel.getAreaCursorSize();
        } else if (channelName == ChannelName.MOUSE_MOVED)
        {
            this.mouseX = iModel.getMouseX();
            this.mouseY =  iModel.getMouseY();
        }
        drawOuterSpace();
        drawMouseArea();
    }
}

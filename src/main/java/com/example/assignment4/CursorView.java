package com.example.assignment4;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class CursorView extends StackPane implements Subscriber
{
    private final int canvasSize;
    Canvas cursorCanvas;
    GraphicsContext gc;
    SpaceModel spaceModel;
    InteractionModel iModel;
    List<Star> stars = new ArrayList<>();
    List<Asteroid> asteroids = new ArrayList<>();
    private double mouseX, mouseY;
    private final double zoomFactor = 2.0;

    public CursorView(int canvasSize)
    {
        this.canvasSize = canvasSize;

        this.cursorCanvas = new Canvas(canvasSize, canvasSize);
        this.gc = cursorCanvas.getGraphicsContext2D();
        drawCursor(0,0);
        this.getChildren().add(cursorCanvas);
    }

    private void drawCursor(double x, double y) {
        // Clear the canvas
        gc.clearRect(0, 0, cursorCanvas.getWidth(), cursorCanvas.getHeight());

        // Draw a rectangle representing the 2X zoomed region centered on the cursor's location
        double rectSize = canvasSize / zoomFactor;
        double startX = x - rectSize / 2;
        double startY = y - rectSize / 2;

        gc.setFill(Color.RED); // Set the cursor color
        gc.fillRect(startX, startY, rectSize, rectSize);
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
        else if (channelName == ChannelName.MOUSE_MOVED)
        {
            //TODO update mouse coords
            System.out.println("Cursor coordinates: " + iModel.getMouseX()  + " " + iModel.getMouseY());
            drawCursor(iModel.getMouseX(), iModel.getMouseY());
        }
    }

}

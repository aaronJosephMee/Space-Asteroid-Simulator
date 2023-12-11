package com.example.assignment4;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import java.util.Arrays;
import java.util.Random;

public class Asteroid extends SpaceObject
{
    private final double[] normalizedXPoints;
    private final double[] normalizedYPoints;
    private final double[] translatedXPoints;
    private final double[] translatedYPoints;
    private final double[] randomSectionLengths;
    private final int canvasSize;
    private Canvas offscreen;
    private WritableImage buffer;
    PixelReader reader;
    private final int sections;
    private double angle;
    private double xVelocity;
    private double yVelocity;
    private final double originalXVelocity;
    private final double originalYVelocity;
    private double aVelocity;
    private boolean selected = false;
    private final double minimumRadius = 0.3;
    private final double maximumRadius = 1.2;
    public int myIndex;


    public Asteroid(double normalizedX, double normalizedY, double normalizedRadius, int myIndex, int canvasSize)
    {
        super(normalizedX, normalizedY, normalizedRadius);
        this.myIndex = myIndex;
        this.canvasSize = canvasSize;
        this.angle = 0.0;

        // Choose a random number of sections between 4 and 8 (plus the last section, which is the
        //  same as the initial one)
        sections = new Random().nextInt(4, 9) + 1;

        normalizedXPoints = new double[sections];
        normalizedYPoints = new double[sections];
        translatedXPoints = new double[sections];
        translatedYPoints = new double[sections];

        randomSectionLengths = new Random()
                .doubles(sections, minimumRadius, maximumRadius)
                .toArray();

        calculateSections(normalizedXPoints, normalizedYPoints, normalizedRadius);

        Random random = new Random();
        xVelocity = random.nextDouble() * 0.0001;
        yVelocity = random.nextDouble() * 0.0001;
        originalXVelocity =  xVelocity;
        originalYVelocity = yVelocity;
        aVelocity = random.nextDouble(0.05, 0.2);

        drawBitmap(canvasSize);
    }

    private void drawBitmap(int canvasSize)
    {
        offscreen = new Canvas(getNormalizedRadius() * canvasSize, getNormalizedRadius() * canvasSize);
        GraphicsContext offscreenGC = offscreen.getGraphicsContext2D();
        buffer = new WritableImage((int) offscreen.getWidth(), (int) offscreen.getHeight());

        offscreenGC.setFill(Color.BLACK);
        offscreenGC.fillRect(0, 0, offscreen.getWidth(), offscreen.getHeight());
        offscreenGC.setFill(Color.WHITE);

        offscreenGC.translate(offscreen.getWidth()/2, offscreen.getHeight()/2);
        double[] xPointsTranslated = Arrays.stream(normalizedXPoints).map(e -> e * offscreen.getWidth()).toArray();
        double[] yPointsTranslated = Arrays.stream(normalizedYPoints).map(e -> e * offscreen.getHeight()).toArray();
        offscreenGC.fillPolygon(xPointsTranslated, yPointsTranslated, normalizedXPoints.length);

        offscreen.snapshot(null, buffer);
        reader = buffer.getPixelReader();
    }

    private void calculateSections(double[] xArray, double[] yArray, double radius)
    {
        // Divide the 2*PI circle into sections and generate points
        for (int sectionNum = 0; sectionNum < sections; sectionNum++)
        {
            double sectionAngle = (2 * Math.PI) * ((double) sectionNum /sections);

            double sectionRadius = radius * randomSectionLengths[sectionNum];

            // Find the point on the circle of the chosen radius
            double x = sectionRadius * Math.cos(sectionAngle);
            double y = sectionRadius * Math.sin(sectionAngle);

            // Add the x and y values to the arrays for the asteroid
            if(sectionNum < (sections - 1))
            {
                xArray[sectionNum] = x;
                yArray[sectionNum] = y;
            }
            else
            {   // Only for the last section, add the initial point
                xArray[sectionNum] = xArray[0];
                yArray[sectionNum] = yArray[0];
            }
        }
    }

    @Override
    public void setTranslatedRadius(double translatedRadius)
    {
        calculateSections(translatedXPoints, translatedYPoints, translatedRadius);
        super.setTranslatedRadius(translatedRadius);
    }
    public double[] getTranslatedXPoints()
    {
        return translatedXPoints;
    }

    public double[] getTranslatedYPoints()
    {
        return translatedYPoints;
    }

    public double getAngle()
    {
        return angle;
    }

    public void setAngle(double angle)
    {
        this.angle = angle;
    }

    public double getXVelocity()
    {
        return xVelocity;
    }

    public void setVelocity(double x, double y)
    {
        this.xVelocity = x;
        this.yVelocity = y;
    }
    public void restoreOriginalVelocity()
    {
        this.xVelocity = originalXVelocity;
        this.yVelocity = originalYVelocity;
    }

    public double getYVelocity()
    {
        return yVelocity;
    }
    public double getAVelocity()
    {
        return aVelocity;
    }
    public void setAVelocity(double aVelocity)
    {
        this.aVelocity = aVelocity;
    }

    public String toString()
    {
        return "X Points: " + Arrays.toString(normalizedXPoints)
                + "\n          Y Points: " + Arrays.toString(normalizedYPoints)
                + "\n          Sections: " + sections
                + "\n          Index: " + myIndex;
    }

    private double rotateX(double x, double y, double radians) {
        return Math.cos(radians) * x - Math.sin(radians) * y;
    }
    private double rotateY(double x, double y, double radians) {
        return Math.sin(radians) * x + Math.cos(radians) * y;
    }

    public boolean contains(double x, double y)
    {
        double rotatedX = x - getNormalizedX() + buffer.getWidth() / (2 * canvasSize);
        double rotatedY = y - getNormalizedY() + buffer.getHeight() / (2 * canvasSize);

        if (rotatedX >= 0 && rotatedY < buffer.getWidth() /canvasSize && rotatedY >= 0 && rotatedX < buffer.getHeight() / canvasSize)
        {
            return true;
        }
        else
        {
            // if we clicked somewhere that wasn't even close to the object, return false
            return false;
        }
    }
    public boolean isSelected()
    {
        return selected;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    public boolean isAsteroidInsideCursor(double xCursor, double yCursor, double radiusCursor)
    {
        for(int i = 0; i < sections; i++)
        {
            double x = getNormalizedX() + normalizedXPoints[i];
            double y = getNormalizedY() + normalizedYPoints[i];
            if(x > (xCursor - radiusCursor / 2) && x < (xCursor + radiusCursor / 2)
                && y > (yCursor - radiusCursor / 2) && y < yCursor + radiusCursor / 2)
            {
                return true;
            }
        }
        return false;
    }
}

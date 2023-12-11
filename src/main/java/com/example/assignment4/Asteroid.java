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
    private final double[] xPoints;
    private final double[] yPoints;
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

        // Choose a random number of sections between 4 and 8
        sections = new Random().nextInt(4, 9);

        xPoints = new double[sections + 1];
        yPoints = new double[sections + 1];
        randomSectionLengths = new Random()
                .doubles(sections + 1, minimumRadius, maximumRadius)
                .toArray();

        calculateSections(normalizedRadius);

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
        //System.out.println("width: " + offscreen.getWidth() + " height: " + offscreen.getHeight());
        GraphicsContext offscreenGC = offscreen.getGraphicsContext2D();
        buffer = new WritableImage((int) offscreen.getWidth(), (int) offscreen.getHeight());

        offscreenGC.setFill(Color.BLACK);
        offscreenGC.fillRect(0, 0, offscreen.getWidth(), offscreen.getHeight());
        offscreenGC.setFill(Color.WHITE);

        offscreenGC.translate(offscreen.getWidth()/2, offscreen.getHeight()/2);
        //TODO check that this is right, do we need to translate/rotate before filling polygon?
        double[] xPointsTranslated = Arrays.stream(xPoints).map(e -> e * offscreen.getWidth()).toArray();
        double[] yPointsTranslated = Arrays.stream(yPoints).map(e -> e * offscreen.getHeight()).toArray();
        //System.out.println("X points bitmap: " + Arrays.toString(xPointsTranslated));
        //System.out.println("Y points bitmap: " + Arrays.toString(yPointsTranslated));
        offscreenGC.fillPolygon(xPointsTranslated, yPointsTranslated, xPoints.length);

        offscreen.snapshot(null, buffer);
        reader = buffer.getPixelReader();
    }

    private void calculateSections(double radius)
    {
        // Divide the 2*PI circle into sections and generate points
        for (int sectionNum = 0; sectionNum <= sections; sectionNum++)
        {
            double sectionAngle = (2 * Math.PI) * ((double) sectionNum /sections);

            double sectionRadius = radius * randomSectionLengths[sectionNum];

            // Find the point on the circle of the chosen radius
            double x = sectionRadius * Math.cos(sectionAngle);
            double y = sectionRadius * Math.sin(sectionAngle);

            // Add the x and y values to the arrays for the asteroid
            if(sections != sectionNum)
            {
                xPoints[sectionNum] = x;
                yPoints[sectionNum] = y;
            }
            else
            {
                xPoints[sectionNum] = xPoints[0];
                yPoints[sectionNum] = yPoints[0];
            }
        }
//        System.out.println("//////// Asteroid " + myIndex);
//        System.out.println("X points: " + Arrays.toString(xPoints));
//        System.out.println("Y points: " + Arrays.toString(yPoints));

    }

    @Override
    public void setTranslatedRadius(double translatedRadius)
    {
//        System.out.println("Normalized rad: " + getNormalizedRadius());
//        System.out.println("Translated rad: " + translatedRadius);
//        System.out.println("XPoints before: " + Arrays.toString(xPoints));
//        System.out.println("YPoints before: " + Arrays.toString(yPoints));
        calculateSections(translatedRadius);
//        System.out.println("XPoints after: " + Arrays.toString(xPoints));
//        System.out.println("YPoints after: " + Arrays.toString(yPoints));
        super.setTranslatedRadius(translatedRadius);
    }

    public double[] getxPoints()
    {
        return xPoints;
    }

    public double[] getyPoints()
    {
        return yPoints;
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
        return "X Points: " + Arrays.toString(xPoints)
                + "\n          Y Points: " + Arrays.toString(yPoints)
                + "\n          Sections: " + sections
                + "\n          Index: " + myIndex;
    }

    private double rotateX(double x, double y, double radians) {
        System.out.println("X coord: " + x);
        return Math.cos(radians) * x - Math.sin(radians) * y;
    }
    private double rotateY(double x, double y, double radians) {
        System.out.println("Y coord: " + y);
        return Math.sin(radians) * x + Math.cos(radians) * y;
    }

    public boolean contains(double x, double y)
    {
        System.out.println("####### Asteroid "  + this.myIndex + " W: " + buffer.getWidth() + " H: " + buffer.getHeight());
        System.out.println("Angle: " + Math.toRadians(this.angle));

        System.out.println("X: " + x + " Y: " + y);
        System.out.println("Norm x in asteroid: " + getNormalizedX() + " and y: " + getNormalizedY());
        double rotatedX = x - getNormalizedX() + buffer.getWidth() / (2 * canvasSize );
        double rotatedY = y - getNormalizedY() + buffer.getHeight() / (2 * canvasSize);

        //TODO use rotateX and rotateY to get the actual mouse coords
//        double rotatedX = rotateX(x, y, Math.toRadians(this.angle));
//        double rotatedY = rotateY(x, y, Math.toRadians(this.angle));
        System.out.println("Rotated X: " + rotatedX);
        System.out.println("Rotated Y: " + rotatedY);
        if (rotatedX >= 0 && rotatedY < buffer.getWidth() /canvasSize && rotatedY >= 0 && rotatedX < buffer.getHeight() / canvasSize)
        {
            System.out.println("INSIDE ASTEROID!!!");
            System.out.println("Colour in click: " + reader.getColor((int) rotatedX, (int) rotatedY));
            boolean rightColour = reader.getColor((int) rotatedX, (int) rotatedY).equals(Color.WHITE);
            System.out.println(rightColour);
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
}

package com.example.assignment4;

import java.util.Arrays;
import java.util.Random;

public class Asteroid extends SpaceObject
{
    private double[] xPoints;
    private double[] yPoints;
    private int sections;
    private double xTranslation;
    private double yTranslation;
    private double angle;
    private double xVelocity;
    private double yVelocity;
    private double aVelocity;
    public int myIndex;


    public Asteroid(double normalizedX, double normalizedY, double radius, int myIndex)
    {
        super(normalizedX, normalizedY, radius);
        this.myIndex = myIndex;
        this.angle = 0.0;

        // Choose a random number of sections between 4 and 8
        sections = new Random().nextInt(4, 9);

        xPoints = new double[sections + 1];
        yPoints = new double[sections + 1];

        // Divide the 2*PI circle into sections and generate points
        for (int sectionNum = 0; sectionNum <= sections; sectionNum++)
        {
            double sectionAngle = (2 * Math.PI) * ((double) sectionNum /sections);

            double sectionRadius = radius * (new Random().nextDouble(0.5, 1.5));

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

        Random random = new Random();
        xVelocity = random.nextDouble() * 0.0001;
        yVelocity = random.nextDouble() * 0.0001;
        aVelocity = random.nextDouble(0.1, 0.5);
    }

    public double[] getxPoints()
    {
        return xPoints;
    }

    public double[] getyPoints()
    {
        return yPoints;
    }


    public double getXTranslation()
    {
        return xTranslation;
    }

    public void setXTranslation(double xTranslation)
    {
        this.xTranslation = xTranslation;
    }

    public double getYTranslation()
    {
        return yTranslation;
    }

    public void setYTranslation(double yTranslation)
    {
        this.yTranslation = yTranslation;
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

    public void setXVelocity(double xVelocity)
    {
        this.xVelocity = xVelocity;
    }

    public double getYVelocity()
    {
        return yVelocity;
    }

    public void setYVelocity(double yVelocity)
    {
        this.yVelocity = yVelocity;
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
}

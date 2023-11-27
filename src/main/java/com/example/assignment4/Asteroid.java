package com.example.assignment4;

import java.util.Arrays;
import java.util.Random;

public class Asteroid extends SpaceObject
{
    private final double[] xPoints;
    private final double[] yPoints;
    private final double[] randomSectionLengths;
    private final int sections;
    private double angle;
    private double xVelocity;
    private double yVelocity;
    private double aVelocity;
    public int myIndex;


    public Asteroid(double normalizedX, double normalizedY, double normalizedRadius, int myIndex)
    {
        super(normalizedX, normalizedY, normalizedRadius);
        this.myIndex = myIndex;
        this.angle = 0.0;

        // Choose a random number of sections between 4 and 8
        sections = new Random().nextInt(4, 9);

        xPoints = new double[sections + 1];
        yPoints = new double[sections + 1];
        randomSectionLengths = new Random()
                .doubles(sections + 1, 0.3, 1.2)
                .toArray();

        calculateSections(normalizedRadius);

        Random random = new Random();
        xVelocity = random.nextDouble() * 0.0001;
        yVelocity = random.nextDouble() * 0.0001;
        aVelocity = random.nextDouble(0.05, 0.2);
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

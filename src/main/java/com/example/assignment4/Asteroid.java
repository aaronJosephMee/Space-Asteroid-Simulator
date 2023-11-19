package com.example.assignment4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Asteroid extends SpaceObject
{
    private double[] xPoints;
    private double[] yPoints;
    private int sections;

    public Asteroid(double normalizedX, double normalizedY, double radius)
    {
        super(normalizedX, normalizedY, radius);

        // Choose a random number of sections between 4 and 8
        sections = new Random().nextInt(4, 9);

        xPoints = new double[sections + 1];
        yPoints = new double[sections + 1];

        // Divide the 2*PI circle into sections and generate points
        for (int sectionNum = 0; sectionNum <= sections; sectionNum++)
        {
            double angle = (2 * Math.PI) * ((double) sectionNum /sections);

            double sectionRadius = radius * (new Random().nextDouble(0.5, 1.5));

            // Find the point on the circle of the chosen radius
            double x = sectionRadius * Math.cos(angle);
            double y = sectionRadius * Math.sin(angle);

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

    public double[] getxPoints()
    {
        return xPoints;
    }

    public double[] getyPoints()
    {
        return yPoints;
    }

    public String toString(){
        return "X Points: " + Arrays.toString(xPoints)
                + "\n          Y Points: " + Arrays.toString(yPoints)
                + "\n          Sections: " + sections;
    }
}

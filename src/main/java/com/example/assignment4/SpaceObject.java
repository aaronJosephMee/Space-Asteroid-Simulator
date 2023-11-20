package com.example.assignment4;

public class SpaceObject
{
    private double normalizedX;
    private double normalizedY;
    private double radius;

    public SpaceObject(double normalizedX, double normalizedY, double radius)
    {
        this.normalizedX = normalizedX;
        this.normalizedY = normalizedY;
        this.radius = radius;
    }

    public double getNormalizedX()
    {
        return normalizedX;
    }

    public void setNormalizedX(double normalizedX)
    {
        if(normalizedX <= 0.0)
        {
            this.normalizedX = 1.0;
        }
        else if (normalizedX >= 1.0)
        {
            this.normalizedX = 0.0;
        }
        else
        {
            this.normalizedX = normalizedX;
        }
    }

    public double getNormalizedY()
    {
        return normalizedY;
    }

    public void setNormalizedY(double normalizedY)
    {
        if(normalizedY <= 0.0)
        {
            this.normalizedY = 1.0;
        }
        else if (normalizedY >= 1.0)
        {
            this.normalizedY = 0.0;
        }
        else
        {
            this.normalizedY = normalizedY;
        }
    }

    public double getRadius()
    {
        return radius;
    }

    public void setRadius(double radius)
    {
        this.radius = radius;
    }
}

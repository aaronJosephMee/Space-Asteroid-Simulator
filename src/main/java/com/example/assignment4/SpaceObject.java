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
        this.normalizedX = normalizedX;
    }

    public double getNormalizedY()
    {
        return normalizedY;
    }

    public void setNormalizedY(double normalizedY)
    {
        this.normalizedY = normalizedY;
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

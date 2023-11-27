package com.example.assignment4;

public class SpaceObject
{
    private double normalizedX;
    private double normalizedY;
    private double normalizedRadius;
    private double translatedX;
    private double translatedY;
    private double translatedRadius;

    public SpaceObject(double normalizedX, double normalizedY, double normalizedRadius)
    {
        this.normalizedX = normalizedX;
        this.normalizedY = normalizedY;
        this.normalizedRadius = normalizedRadius;
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

    public double getNormalizedRadius()
    {
        return normalizedRadius;
    }

    public void setNormalizedRadius(double normalizedRadius)
    {
        this.normalizedRadius = normalizedRadius;
    }

    public double getTranslatedX()
    {
        return translatedX;
    }

    public void setTranslatedX(double translatedX)
    {
        this.translatedX = translatedX;
    }

    public double getTranslatedY()
    {
        return translatedY;
    }

    public void setTranslatedY(double translatedY)
    {
        this.translatedY = translatedY;
    }

    public double getTranslatedRadius()
    {
        return translatedRadius;
    }

    public void setTranslatedRadius(double translatedRadius)
    {
        this.translatedRadius = translatedRadius;
    }
}

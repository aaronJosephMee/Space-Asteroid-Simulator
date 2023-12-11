package com.example.assignment4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpaceModel
{
    private final List<Asteroid> asteroidList;
    private final List<Star> stars;
    private PublishSubscribe publisher;
    private boolean asteroidsMoving = true;
    private boolean asteroidsSpinning = true;

    public SpaceModel()
    {
        asteroidList = new ArrayList<>();
        stars = new ArrayList<>();

        generateStars(100);
    }

    public void generateStars(int numOfStars)
    {
        Random random = new Random();
        for (int i = 0; i < numOfStars; i++)
        {
            stars.add(new Star(random.nextDouble(), random.nextDouble(), random.nextDouble()));
        }
    }

    public List<Asteroid> getAsteroidList()
    {
        return asteroidList;
    }

    public List<Star> getStars()
    {
        return stars;
    }

    public void createAsteroid(int canvasSize)
    {
        Random random = new Random();

        Asteroid asteroid = new Asteroid(random.nextDouble(), random.nextDouble(),
                random.nextDouble(0.04, 0.06), asteroidList.size(), canvasSize);

        asteroidList.add(asteroid);

        publisher.publishToChannel(ChannelName.CREATE_ASTEROID);
    }

    public void moveAsteroids()
    {
        if(asteroidsMoving)
        {
            for (Asteroid asteroid : asteroidList)
            {
                //TODO this sets the normalized x and y instead of the translated ones,
                // change if necessary
                asteroid.setNormalizedX(asteroid.getNormalizedX() + asteroid.getXVelocity());
                asteroid.setNormalizedY(asteroid.getNormalizedY() + asteroid.getYVelocity());
            }
        }
        //TODO change this to update-asteroid
        //publisher.publishToChannel("create-asteroid", asteroidList);
    }

    public void spinAsteroids()
    {
        if(asteroidsSpinning)
        {
            for (Asteroid asteroid : asteroidList)
            {
                // Update asteroid's angle based on its angular velocity
                asteroid.setAngle(asteroid.getAngle() + asteroid.getAVelocity());
            }
        }
        //TODO change this to update-asteroid
        publisher.publishToChannel(ChannelName.CREATE_ASTEROID);
    }

    public void setAsteroidsMoving(boolean asteroidsMoving)
    {
        this.asteroidsMoving = asteroidsMoving;
        //TODO add this to update-asteroid
    }

    public void dragAsteroids(List<Asteroid> asteroids, double dx, double dy)
    {
        for(Asteroid asteroid : asteroids)
        {
            asteroid.setNormalizedX(asteroid.getNormalizedX() + dx);
            asteroid.setNormalizedY(asteroid.getNormalizedY() + dy);
        }
        publisher.publishToChannel(ChannelName.CREATE_ASTEROID);
    }

    public void setAsteroidsVelocity(List<Asteroid> asteroids, double vX, double vY)
    {
        for (Asteroid asteroid : asteroids)
        {
            asteroid.setVelocity(vX, vY);
        }
        publisher.publishToChannel(ChannelName.CREATE_ASTEROID);
    }

    public void setAsteroidsSpinning(boolean asteroidsSpinning)
    {
        this.asteroidsSpinning = asteroidsSpinning;
    }

    public boolean areAsteroidsMoving()
    {
        return asteroidsMoving;
    }

    public boolean areAsteroidsSpinning()
    {
        return asteroidsSpinning;
    }

    public void setPublisher(PublishSubscribe publisher)
    {
        this.publisher = publisher;
        publisher.publishToChannel(ChannelName.CREATE_STAR);
    }

    public Asteroid getAsteroidAtCoords(double x, double y)
    {
        for(Asteroid asteroid : asteroidList.reversed())
        {
            //TODO take into account the world rotation
            if(asteroid.contains(x, y))
            {
                return asteroid;
            }
        }
        return null;
    }
}

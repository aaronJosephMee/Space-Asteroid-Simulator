package com.example.assignment4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpaceModel
{
    private Asteroid asteroid;
    private List<Asteroid> asteroidList;
    private List<Star> stars;

    private PublishSubscribe publisher;


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

    public void createAsteroid()
    {
        Random random = new Random();

        asteroid = new Asteroid(random.nextDouble(), random.nextDouble(),
                random.nextDouble(0.7, 1.1), asteroidList.size());

        asteroidList.add(asteroid);

        publisher.publishToChannel(ChannelName.CREATE_ASTEROID);
    }

    public void moveAsteroids()
    {
        for (Asteroid asteroid : asteroidList)
        {
                //TODO this sets the normalized x and y instead of the translated ones,
                // change if necessary
                asteroid.setNormalizedX(asteroid.getNormalizedX() + asteroid.getXVelocity());
                asteroid.setNormalizedY(asteroid.getNormalizedY() + asteroid.getYVelocity());
        }
        //TODO change this to update-asteroid
        //publisher.publishToChannel("create-asteroid", asteroidList);
    }

    public void spinAsteroids()
    {
        for (Asteroid asteroid : asteroidList)
        {
                // Update asteroid's angle based on its angular velocity
                asteroid.setAngle(asteroid.getAngle() + asteroid.getAVelocity());
        }
        //TODO change this to update-asteroid
        publisher.publishToChannel(ChannelName.CREATE_ASTEROID);
    }

    public void setPublisher(PublishSubscribe publisher)
    {
        this.publisher = publisher;
        publisher.publishToChannel(ChannelName.CREATE_STAR);
    }

}

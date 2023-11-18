package com.example.assignment4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpaceModel
{
    private Asteroid asteroid;
    private List<SpaceObject> asteroidList;
    private List<SpaceObject> stars;

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

    public List<SpaceObject> getAsteroidList()
    {
        return asteroidList;
    }

    public List<SpaceObject> getStars()
    {
        return stars;
    }

    public void createAsteroid()
    {
        Random random = new Random();

        asteroid = new Asteroid(random.nextDouble(), random.nextDouble(),
                10 + random.nextDouble(8));

        asteroidList.add(asteroid);

        publisher.publishToChannel("create-asteroid", asteroidList);
    }

    public void setPublisher(PublishSubscribe publisher)
    {
        this.publisher = publisher;
        publisher.publishToChannel("create-star", stars);
    }

}

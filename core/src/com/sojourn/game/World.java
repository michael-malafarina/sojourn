package com.sojourn.game;

import com.badlogic.gdx.math.Vector2;

public class World
{
    public static final int WIDTH = 2000*6; //1920 * 6;
    public static final int HEIGHT = 1500*6; //1920 * 6;

    public static int getWestEdge()
    {
        return -WIDTH /2;
    }

    public static int getEastEdge()
    {
        return WIDTH /2;
    }

    public static int getNorthEdge()
    {
        return HEIGHT /2;
    }

    public static int getSouthEdge()
    {
        return -HEIGHT /2;
    }

    public static Vector2 getNorthWestCorner()
    {
        return new Vector2(getWestEdge(), getNorthEdge());
    }

    public static Vector2 getNorthEastCorner()
    {
        return new Vector2(getEastEdge(), getNorthEdge());
    }

    public static Vector2 getSouthWestCorner()
    {
        return new Vector2(getWestEdge(), getSouthEdge());
    }

    public static Vector2 getSouthEastCorner()
    {
        return new Vector2(getEastEdge(), getSouthEdge());
    }


    public static Vector2 getRandomWestSide(float width)
    {
        return new Vector2(Utility.random(getWestEdge(), getWestEdge() + width), getRandomY());
    }

    public static Vector2 getRandomEastSide(float width)
    {
        return new Vector2(Utility.random(getEastEdge() - width, getEastEdge()), getRandomY());
    }

    public static Vector2 getRandomNorthSide(float height)
    {
        return new Vector2(getRandomX(), Utility.random(getNorthEdge() - height, getNorthEdge()));
    }

    public static Vector2 getRandomSouthSide(float height)
    {
        return new Vector2(getRandomX(), Utility.random(getSouthEdge(), getSouthEdge() + height));
    }

    public static float getRandomX()
    {
        return Utility.random(getWestEdge(), getEastEdge());
    }

    public static float getRandomY()
    {
        return Utility.random(getSouthEdge(), getNorthEdge());
    }


}
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


    public static Vector2 getRandomWestSide()
    {
        return new Vector2(getWestEdge(), Utility.random(getSouthEdge(), getNorthEdge()));
    }

    public static Vector2 getRandomEastSide()
    {
        return new Vector2(getEastEdge(), Utility.random(getSouthEdge(), getNorthEdge()));
    }

    public static Vector2 getRandomNorthSide()
    {
        return new Vector2(Utility.random(getWestEdge(), getEastEdge()), getNorthEdge());
    }

    public static Vector2 getRandomSouthSide()
    {
        return new Vector2(Utility.random(getWestEdge(), getEastEdge()), getSouthEdge());
    }



}

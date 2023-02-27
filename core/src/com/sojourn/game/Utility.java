package com.sojourn.game;

import com.badlogic.gdx.math.Vector2;

public class Utility
{
    public static int random(int max)
    {
        return (int) (Math.random() * max);
    }

    public static float random(float max)
    {
        return (int) (Math.random() * max);
    }

    public static int random(int min, int max)
    {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    public static float random(double min, double max)
    {
        return (float) (min + (Math.random() * (max - min)));
    }

    public static Vector2 makeVector(float magnitude, float angle)
    {
        Vector2 delta = new Vector2(.5f, .5f);
        delta.nor();
        delta.setAngleDeg(angle).scl(magnitude);
        return delta;
    }

    public static float scaleBetween(float n, float minOut, float maxOut, float minIn, float maxIn)
    {
        return (maxOut - minOut) * (n - minIn) / (maxIn - minIn) + minOut;
    }

    public static float scaleBetweenBounded(float n, float minOut, float maxOut, float minIn, float maxIn)
    {
        float val = scaleBetween(n, minOut, maxOut, minIn, maxIn);
        return Math.max(Math.min(val,  maxOut), minOut);
    }
}

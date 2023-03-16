package com.sojourn.game;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Utility
{
    public static int random(int max)
    {
        return (int) (Math.random() * max);
    }

    public static float random(float max)
    {
        return (float) (Math.random() * max);
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

    public static List<Integer> uniqueRolls(int numRolls, int minValue, int maxValue)
    {
        List<Integer> rolls = new ArrayList<>();

        for(int i = 0; i < numRolls; i++) {
            int r = Utility.random(minValue, maxValue);
            boolean found = false;

            for (int cur : rolls) {
                if (cur == r) {
                    found = true;
                }
            }

            if (found) {
                i--;

            }
            else {
                rolls.add(r);
            }
        }

        return rolls;

    }

//    public String getColorCode(Color c)
//    {
//        return
//    }
}

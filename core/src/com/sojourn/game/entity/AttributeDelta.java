package com.sojourn.game.entity;

public class AttributeDelta
{
    private int time;
    private float amount;

    AttributeDelta(int time, float amount)
    {
        this.time = time;
        this.amount = amount;
    }

    public float getAmount()
    {
        return amount;
    }

    public int getTime()
    {
        return time;
    }

    public String toString()
    {
        return amount + " at " + time + " frames";
    }

}

package com.sojourn.game.entity;

public class Attribute
{
    float current;
    float maximum;
    float maximumBase;
    float regeneration;

    public Attribute(float value)
    {
        current = value;
        maximum = value;
        maximumBase = value;
        regeneration = 0;
    }

    public Attribute(float current, float maximum)
    {
        this.current = current;
        this.maximum = maximum;
        this.maximumBase = maximum;
        regeneration = 0;
    }

    /************ ACCESSORS *************/

    public float getCurrent()
    {
        return current;
    }

    public float getMaximum()
    {
        return maximum;
    }

    public float getRegeneration()
    {
        return regeneration;
    }

    public float getPercent()    {
        return current / maximum;
    }

    /************ MUTATORS *************/

    public void update()
    {
        increase(getRegeneration());
    }

     public void increase(float amount)
    {
        current = Math.min(getCurrent() + amount,  getMaximum());
    }

    public void decrease(float amount)
    {
        current = Math.max(getCurrent() - amount,  0);
    }

    public void maximize()
    {
        current = maximum;
    }
}

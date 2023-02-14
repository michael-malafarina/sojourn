package com.sojourn.game.entity;

public class Unit extends Entity
{
    public Unit()
    {
        super();
    }

    public int getNumLayers()
    {
        return 5;
    }

    @Override
    public int getMaxSpeedBase()
    {
        return 50;
    }

    @Override
    public int getAccelerationBase()
    {
        return 25;
    }

    @Override
    public float getMaxSpeed() {
        return 25;
    }
}

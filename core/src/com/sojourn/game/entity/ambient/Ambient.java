package com.sojourn.game.entity.ambient;

import com.sojourn.game.entity.Entity;

abstract public class Ambient extends Entity
{
    @Override
    public int getValueBase() {
        return 0;
    }

    @Override
    public int getNumLayers() {
        return 1;
    }

    @Override
    public float getMaxSpeedBase() {
        return 0;
    }

    @Override
    public float getAccelerationBase() {
        return 0;
    }

    @Override
    public void actionPlanning() {

    }

    @Override
    public void actionCombat() {

    }
}

package com.sojourn.game.entity.ambient;

import com.sojourn.game.entity.Entity;

abstract public class Ambient extends Entity
{
    public void setAttributes()
    {
        setSpeed(0);
        setAcceleration(0);
        super.setAttributes();
    }

    @Override
    public int getNumLayers() {
        return 1;
    }

    @Override
    public void actionPlanning() {

    }

    @Override
    public void actionCombat() {

    }
}

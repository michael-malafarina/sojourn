package com.sojourn.game.entity.unit.civilian;

import com.sojourn.game.entity.unit.Unit;

abstract public class Civilian extends Unit
{

    public void setAttributes()
    {
        setSpeed(0);
        setAcceleration(0);
        super.setAttributes();
    }

    @Override
    public void actionPlanning() {

    }

    @Override
    public void actionCombat() {

    }

    public void onBuild()
    {

    }

    public void spawnUnit(Class<? extends Unit> clazz)
    {
//        EntityManager.addUnit(clazz, getPosition(), getTeam(), this);
    }
}

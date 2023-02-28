package com.sojourn.game.entity.unit.civilian;

import com.sojourn.game.entity.unit.Unit;

abstract public class Civilian extends Unit
{

    @Override
    public void actionPlanning() {

    }

    @Override
    public void actionCombat() {

    }

    public void spawnUnit(Class<? extends Unit> clazz)
    {
//        EntityManager.addUnit(clazz, getPosition(), getTeam(), this);
    }
}

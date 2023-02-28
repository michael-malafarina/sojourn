package com.sojourn.game.entity.unit;

import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.EntityManager;
import com.sojourn.game.entity.component.weapon.WeaponSet;
import com.sojourn.game.entity.unit.ship.Ship;

import java.util.List;

abstract public class Unit extends Entity
{
    protected WeaponSet weapons;

    public Unit()
    {
        super();
        weapons = new WeaponSet();

    }



    public void update(boolean planning, float delta)
    {
        upkeep(delta);
        weapons.update();
        action(planning);
    }

    public boolean inRangeShortest(Entity e)
    {
        return getDistance(e) <= weapons.getShortestRange();
    }

    public boolean inRangeLongest(Entity e)
    {
        return getDistance(e) <= weapons.getLongestRange();
    }

    protected List<Unit> getEnemyUnits()
    {
        return EntityManager.getEnemyUnits(getTeam());
    }

    protected Unit getNearestUnit()
    {
        return EntityManager.getNearestUnit(this);
    }

    protected Ship getNearestShip()
    {
        return EntityManager.getNearestShip(this);
    }

    protected Unit getNearestEnemyUnit()
    {
        return EntityManager.getNearestEnemyUnit(this);
    }

    protected Ship getNearestEnemyShip()
    {
        return EntityManager.getNearestEnemyShip(this);
    }



}

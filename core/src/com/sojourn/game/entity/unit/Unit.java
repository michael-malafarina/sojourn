package com.sojourn.game.entity.unit;

import com.sojourn.game.entity.AttributePool;
import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.EntityManager;
import com.sojourn.game.entity.component.module.ModuleSet;
import com.sojourn.game.entity.component.module.MunitionsDepot;
import com.sojourn.game.entity.component.weapon.WeaponSet;
import com.sojourn.game.entity.unit.ship.Ship;

import java.util.List;

abstract public class Unit extends Entity
{
    private final float MUNITIONS_REGEN_PLANNING = 1/120f;
    protected WeaponSet weapons;
    protected ModuleSet modules;

    protected AttributePool munitions;

    public Unit()
    {
        super();
        weapons = new WeaponSet();
        modules = new ModuleSet();
//
    }

    public WeaponSet getWeapons()
    {
        return weapons;
    }

    public AttributePool getMunitions()    {
        return munitions;
    }

    protected void upkeep(boolean planning, float delta)
    {
        super.upkeep(planning, delta);

        if(munitions != null)
        {
            munitions.update();

            if(planning)
            {
                munitions.increase(MUNITIONS_REGEN_PLANNING * munitions.getMaximum());
            }

        }
    }

    public boolean isMunitionsDepot()
    {
        return modules.hasModule(MunitionsDepot.class);
    }

    public void setAttributes()
    {
        setMunitions(0);
        super.setAttributes();
    }

    public void update(boolean planning, float delta)
    {
        upkeep(planning, delta);
        weapons.update();
        modules.update();
        action(planning);
    }

    protected void setMunitions(int baseValue)
    {
        munitions = new AttributePool(getTeam().getTeamBonusManager().getMunitionsBonus(), baseValue);
    }

    protected void setMunitionsRegeneration(int amount)
    {
        munitions.setRegeneration(amount);
    }

    public boolean inRangeShortest(com.sojourn.game.entity.Entity e)
    {
        return getDistance(e) <= weapons.getShortestRange();
    }

    public boolean inRangeLongest(com.sojourn.game.entity.Entity e)
    {
        return getDistance(e) <= weapons.getLongestRange();
    }

    protected List<Unit> getEnemyUnits()
    {
        return EntityManager.getHostileUnits(getTeam());
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

package com.sojourn.game.entity.component.module;

import com.sojourn.game.entity.EntityManager;
import com.sojourn.game.entity.unit.Unit;
import com.sojourn.game.entity.unit.ship.Ship;

import java.util.List;

public abstract class ModuleTargetShip extends Module
{
    protected float range;
    protected float cooldown;
    protected float timer;

    public ModuleTargetShip(Unit owner)
    {
        super(owner);

        range = (owner.getWidth() + owner.getHeight()) / 2;
        cooldown = 60;
        timer = 0;
    }

    public void update()
    {
        if(timer < cooldown)
        {
            timer++;
            return;
        }

        timer = 0;

        List<Ship> ships = EntityManager.getFriendyShips(owner.getTeam());

        for(Ship s : ships)
        {
            if(canApply(s))
            {
                apply(s);
                return;
            }
        }


    }


    protected boolean canApply(Ship target)
    {
        // Do not apply to units out of range
        if(owner.getDistance(target) > range)
        {
            return false;
        }

        if(target == owner)
        {
            return false;
        }

        return true;
    }

    protected void apply(Ship target)
    {



    }
}

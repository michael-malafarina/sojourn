package com.sojourn.game.entity.component.module;

import com.sojourn.game.entity.EntityManager;
import com.sojourn.game.entity.unit.Unit;

import java.util.List;

public class MunitionsDepot extends Module
{
    float depotRange;

    public MunitionsDepot(Unit owner)
    {
        super(owner);

        depotRange = (owner.getWidth() + owner.getHeight()) / 2;
    }

    public void update()
    {
        List<Unit> units = EntityManager.getFriendlyUnits(owner.getTeam());
        units.forEach(u -> transferMunitions(u));
    }

    public void transferMunitions(Unit target)
    {
        // Do not apply to units without munitions
        if(target.getMunitions().getMaximum() == 0)
        {
            return;
        }

        // Do not apply to units out of range
        if(owner.getDistance(target) > depotRange)
        {
            return;
        }

        // Do not apply to full targets
        if(Math.round(target.getMunitions().getPercent()) == 1f)
        {
            return;
        }

        // Cannot apply to self;
        if(target == owner)
        {
            return;
        }

        float amountNeeded = target.getMunitions().getMaximum() - target.getMunitions().getCurrent();
        float stockpile = owner.getMunitions().getCurrent();
        float actualAmount = amountNeeded;

        // If I do not have enough to fully resupply them, give all the rest
        if(actualAmount > stockpile)
        {
            actualAmount = stockpile;
        }

        target.getMunitions().increase(actualAmount);
        owner.getMunitions().decrease(actualAmount);


    }
}

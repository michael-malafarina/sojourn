package com.sojourn.game.entity.component.module;

import com.sojourn.game.entity.unit.Unit;
import com.sojourn.game.entity.unit.ship.Ship;

public class MunitionsDepot extends ModuleTargetShip
{
    float munitionsPerFrame = .2f*60;

    public MunitionsDepot(Unit owner)
    {
        super(owner);
    }

    public boolean canApply(Ship target)
    {
        // Do not apply to units without munitions
        if(target.getMunitions().getMaximum() == 0)
        {
            return false;
        }

        // Do not apply to full targets
        if(target.getMunitions().getCurrent() == target.getMunitions().getMaximum())
        {
            return false;
        }

        return super.canApply(target);
    }

    public void apply(Ship target)
    {
        super.apply(target);
        target.getMunitions().increase(munitionsPerFrame);
    }


}

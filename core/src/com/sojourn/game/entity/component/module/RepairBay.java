package com.sojourn.game.entity.component.module;

import com.sojourn.game.entity.unit.Unit;
import com.sojourn.game.entity.unit.ship.Ship;

public class RepairBay extends ModuleTargetShip
{
    float repairPerFrame = .2f*60;

    public RepairBay(Unit owner)
    {
        super(owner);
    }

    public boolean canApply(Ship target)
    {
        // Do not apply to full targets
        if(target.getHealth().getCurrent() == target.getHealth().getMaximum())
        {
            return false;
        }

        return super.canApply(target);
    }

    public void apply(Ship target)
    {
        super.apply(target);
        target.restoreHealth(repairPerFrame, getOwner());
    }
}

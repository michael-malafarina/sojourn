package com.sojourn.game.entity.component.weapon;

import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.unit.Unit;

public class MachineGun extends WeaponKinetic
{
    public MachineGun(Unit owner)
    {
        super(owner);
        setRange(300);
        setDamage(5);
        setMunitionCost(1);
        size = 10;
    }


    public void effect(Entity target)
    {
        dealDamage(target);
    }


    @Override
    public int getPreparationTime() { return 20;  }

    @Override
    public int getActivationTime() { return 40;  }

    @Override
    public int getRecoveryTime() {
        return 60;
    }

}

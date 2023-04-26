package com.sojourn.game.entity.component.weapon;

import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.unit.Unit;

public class LargeLaser extends WeaponLaser
{
    public LargeLaser(Unit owner)
    {
        super(owner);
        setRange(500);
        setDamage(12);
        size = 3;

    }



    @Override
    public float getAnimationAlpha() {
        return .5f;
    }

    @Override
    public boolean getAnimationBurst() {
        return false;
    }

    public void effect(Entity target)
    {
        dealDamage(target);
    }

    @Override
    public int getPreparationTime() { return 40;  }

    @Override
    public int getActivationTime() { return 80;  }

    @Override
    public int getRecoveryTime() {
        return 80;
    }

}

package com.sojourn.game.entity.component.weapon;

import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.unit.Unit;

public class SmallLaser extends WeaponLaser
{
    public SmallLaser(Unit owner)
    {
        super(owner);
        setRange(350);
        setDamage(7);
    }

    @Override
    public int getAnimationWidth() {
        return 2;
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
    public int getMunitionCost() {
        return 0;
    }

    @Override
    public int getPreparationTime() { return 30;  }

    @Override
    public int getActivationTime() { return 60;  }

    @Override
    public int getRecoveryTime() {
        return 60;
    }

}

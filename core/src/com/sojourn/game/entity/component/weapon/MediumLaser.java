package com.sojourn.game.entity.component.weapon;

import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.unit.Unit;

public class MediumLaser extends WeaponLaser
{
    public MediumLaser(Unit owner)
    {
        super(owner);
        setRange(500);
        setDamage(12);
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

    public void effect(Entity owner, Entity target)
    {
        target.takeDamage(getDamage().getValue(), owner);
    }

    @Override
    public int getMunitionCost() {
        return 0;
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

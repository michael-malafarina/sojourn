package com.sojourn.game.entity.component.weapon;

import com.sojourn.game.entity.Entity;

public class SmallLaser extends WeaponLaser
{
    public SmallLaser(Entity owner)
    {
        super(owner);
        setRange(300);
        setDamage(5);
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
    public int getPreparationTime() { return 30;  }

    @Override
    public int getActivationTime() { return 60;  }

    @Override
    public int getRecoveryTime() {
        return 60;
    }

}

package com.sojourn.game.entity.component.weapon;

import com.sojourn.game.entity.Entity;

public class LargeLaser extends WeaponLaser
{
    public LargeLaser(Entity owner)
    {
        super(owner);
    }

    @Override
    public int getAnimationWidth() {
        return 8;
    }

    @Override
    public float getAnimationAlpha() {
        return .6f;
    }

    @Override
    public boolean getAnimationBurst() {
        return true;
    }

    @Override
    public int getRangeBase() {
        return 600;
    }

    public void effect(Entity owner, Entity target)
    {
        target.takeDamage(15, owner);
    }

    @Override
    public int getPreparationTime() { return 60;  }

    @Override
    public int getActivationTime() { return 60;  }

    @Override
    public int getRecoveryTime() {
        return 120;
    }

}

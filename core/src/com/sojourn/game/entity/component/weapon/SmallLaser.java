package com.sojourn.game.entity.component.weapon;

import com.sojourn.game.entity.Entity;

public class SmallLaser extends WeaponLaser
{
    public SmallLaser(Entity owner)
    {
        super(owner);
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


    @Override
    public int getRangeBase() {
        return 300;
    }

    public void effect(Entity owner, Entity target)
    {
        target.takeDamage(5, owner);
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

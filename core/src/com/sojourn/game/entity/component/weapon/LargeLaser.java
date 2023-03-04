package com.sojourn.game.entity.component.weapon;

import com.sojourn.game.entity.Entity;

public class LargeLaser extends WeaponLaser
{
    public LargeLaser(Entity owner)
    {
        super(owner);
        setRange(600);
        setDamage(15);
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

    public void effect(Entity owner, Entity target)
    {
        target.takeDamage(getDamage().getValue(), owner);
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

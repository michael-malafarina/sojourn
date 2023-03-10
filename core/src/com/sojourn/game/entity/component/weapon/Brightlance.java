package com.sojourn.game.entity.component.weapon;

import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.unit.Unit;

public class Brightlance extends WeaponLaser
{
    public Brightlance(Unit owner)
    {
        super(owner);
        setRange(650);
        setDamage(25);
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
    public int getMunitionCost() {
        return 0;
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

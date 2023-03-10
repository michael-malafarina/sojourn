package com.sojourn.game.entity.component.weapon;

import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.unit.Unit;

public class Autocannon extends WeaponKinetic
{
    public Autocannon(Unit owner)
    {
        super(owner);
        setRange(400);
        setDamage(12);
    }

    @Override
    public int getBulletWidth() {
        return 15;
    }

    @Override
    public int getBulletHeight() {
        return 15;
    }

    public void effect(Entity owner, Entity target)
    {
        target.takeDamage(getDamage().getValue(), owner);
    }

    @Override
    public int getMunitionCost() {
        return 2;
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

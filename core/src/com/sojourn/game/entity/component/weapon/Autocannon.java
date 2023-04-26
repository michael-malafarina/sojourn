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
        setMunitionCost(2);
        size = 15;
        setUseTimes(20, 40, 60);
    }


    public void effect(Entity target)
    {
        dealDamage(target);
    }


}

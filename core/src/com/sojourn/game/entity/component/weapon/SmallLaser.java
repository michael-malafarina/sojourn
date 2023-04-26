package com.sojourn.game.entity.component.weapon;

import com.sojourn.game.entity.unit.Unit;

public class SmallLaser extends WeaponLaser
{
    public SmallLaser(Unit owner)
    {
        super(owner);
        setRange(350);
        setDamage(7);
        size = 2;
        alpha = .5f;
        setUseTimes(30, 60, 60);
    }
}

package com.sojourn.game.entity.unit;

import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.component.weapon.WeaponSet;

abstract public class Unit extends Entity
{
    protected WeaponSet weapons;

    public Unit()
    {
        super();
        weapons = new WeaponSet();

    }

    public void update(boolean planning, float delta)
    {
        super.update(planning, delta);
        weapons.update();
    }
}

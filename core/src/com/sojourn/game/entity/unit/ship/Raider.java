package com.sojourn.game.entity.unit.ship;

import com.badlogic.gdx.graphics.Texture;
import com.sojourn.game.Textures;
import com.sojourn.game.entity.Attribute;
import com.sojourn.game.entity.component.weapon.Laser;

public class Raider extends Ship
{
    public Raider()
    {
        super();
        health = new Attribute(120);

        weapons.add(new Laser(this));

    }

    public void actionCombat()
    {
        super.actionCombat();
        weapons.getWeapons().get(0).use(getNearestEnemyUnit());

    }

    @Override
    public Texture getSpriteSheet()
    {
        return Textures.raider;
    }

    @Override
    public int getWidth() {
        return 32;
    }

    @Override
    public int getHeight() {
        return 32;
    }

    @Override
    public float getMaxSpeedBase()
    {
        return 150;
    }

    @Override
    public float getAccelerationBase()
    {
        return 4;
    }
}

package com.sojourn.game.entity.unit.ship;

import com.badlogic.gdx.graphics.Texture;
import com.sojourn.game.Textures;
import com.sojourn.game.entity.component.weapon.SmallLaser;

public class EnergyTank extends Tank
{
    public void startingAttributes()
    {
        setHealth(200);
        setSquadSize(4);

        weapons.add(new SmallLaser(this));
    }

    public void actionCombat()
    {
        super.actionCombat();

    }

    @Override
    public Texture getSpriteSheet()
    {
        return Textures.bubble;
    }

    @Override
    public int getValueBase() {
        return 7;
    }

    @Override
    public int getWidth() {
        return 48;
    }

    @Override
    public int getHeight() {
        return 48;
    }

    @Override
    public float getMaxSpeedBase()
    {
        return 50;
    }

    @Override
    public float getAccelerationBase()
    {
        return 50;
    }
}

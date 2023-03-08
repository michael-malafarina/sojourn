package com.sojourn.game.entity.unit.ship;

import com.badlogic.gdx.graphics.Texture;
import com.sojourn.game.Textures;
import com.sojourn.game.entity.component.weapon.LargeLaser;

public class EnergySniper extends Sniper
{
    public void startingAttributes()
    {
        setHealth(75);
        setSquadSize(4);
        setSpeed(30);
        setAcceleration(30);

        weapons.add(new LargeLaser(this));
    }

    public void actionCombat()
    {
        super.actionCombat();

    }

    @Override
    public Texture getSpriteSheet()
    {
        return Textures.boxy;
    }

    @Override
    public int getValueBase() {
        return 6;
    }

    @Override
    public int getWidth() {
        return 32;
    }

    @Override
    public int getHeight() {
        return 32;
    }


}

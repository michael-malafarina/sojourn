package com.sojourn.game.entity.unit.ship;

import com.badlogic.gdx.graphics.Texture;
import com.sojourn.game.Textures;

public class Raider extends Ship
{
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
    public int getMaxSpeedBase()
    {
        return 3;
    }

    @Override
    public int getAccelerationBase()
    {
        return 10;
    }
}

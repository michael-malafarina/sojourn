package com.sojourn.game.entity.unit.ship;

import com.badlogic.gdx.graphics.Texture;
import com.sojourn.game.Textures;
import com.sojourn.game.entity.Attribute;

public class Scout extends Ship
{
    public Scout()
    {
        super();
        health = new Attribute(50);
    }

    @Override
    public Texture getSpriteSheet()
    {
        return Textures.scout;
    }

    @Override
    public int getWidth() {
        return 24;
    }

    @Override
    public int getHeight() {
        return 24;
    }

    @Override
    public int getMaxSpeedBase()
    {
        return 6;
    }

    @Override
    public int getAccelerationBase()
    {
        return 20;
    }
}

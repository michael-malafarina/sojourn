package com.sojourn.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.sojourn.game.Textures;

public class Scout extends Unit
{
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

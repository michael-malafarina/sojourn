package com.sojourn.game.entity.unit.civilian;

import com.badlogic.gdx.graphics.Texture;
import com.sojourn.game.Textures;

public class Carrier extends Civilian
{
    @Override
    public int getWidth() {
        return 256;
    }

    @Override
    public int getHeight() {
        return 256;
    }

    @Override
    public int getNumLayers() {
        return 3;
    }

    @Override
    public int getMaxSpeedBase() {
        return 0;
    }

    @Override
    public int getAccelerationBase() {
        return 0;
    }

    @Override
    public Texture getSpriteSheet() {
        return Textures.base;
    }

}

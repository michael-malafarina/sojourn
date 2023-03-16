package com.sojourn.game.entity.unit.civilian;

import com.badlogic.gdx.graphics.Texture;
import com.sojourn.game.Textures;

public class SupplyShip extends Civilian
{
    public SupplyShip()
    {
        //       setPosition(getX() - getWidth()/2, getY() - getHeight()/2);
        description = "A supply ship";

    }

    public void startingAttributes()
    {
        setHealth(5000);

    }

    @Override
    public int getWidth() {
        return 128;
    }

    @Override
    public int getHeight() {
        return 128;
    }

    @Override
    public int getNumLayers() {
        return 3;
    }

    @Override
    public Texture getSpriteSheet() {
        return Textures.base;
    }

}
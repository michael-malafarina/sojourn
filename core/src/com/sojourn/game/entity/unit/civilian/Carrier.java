package com.sojourn.game.entity.unit.civilian;

import com.badlogic.gdx.graphics.Texture;
import com.sojourn.game.Textures;

public class Carrier extends Civilian
{
    public Carrier()
    {
        //       setPosition(getX() - getWidth()/2, getY() - getHeight()/2);

    }

    public void startingAttributes()
    {
        setHealth(1000);

    }

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
    public Texture getSpriteSheet() {
        return Textures.base;
    }

}

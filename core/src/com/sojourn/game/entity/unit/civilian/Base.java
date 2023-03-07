package com.sojourn.game.entity.unit.civilian;

import com.badlogic.gdx.graphics.Texture;
import com.sojourn.game.Textures;
import com.sojourn.game.entity.component.module.MunitionsDepot;

public class Base extends Civilian
{
    public Base()
    {
//       setPosition(getX() - getWidth()/2, getY() - getHeight()/2);

    }

    public void startingAttributes()
    {
        setHealth(5000);
        setMunitions(150);
        modules.add(new MunitionsDepot(this));
    }

    public int getValueBase() {
        return 25;
    }


    @Override
    public int getWidth() {
        return 256*2;
    }

    @Override
    public int getHeight() {
        return 256*2;
    }

    @Override
    public int getNumLayers() {
        return 3;
    }

    @Override
    public float getMaxSpeedBase() {
        return 0;
    }

    @Override
    public float getAccelerationBase() {
        return 0;
    }

    @Override
    public Texture getSpriteSheet() {
        return Textures.base;
    }

}

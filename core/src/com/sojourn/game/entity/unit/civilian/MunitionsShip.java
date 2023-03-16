package com.sojourn.game.entity.unit.civilian;

import com.badlogic.gdx.graphics.Texture;
import com.sojourn.game.Textures;
import com.sojourn.game.entity.component.module.MunitionsDepot;

public class MunitionsShip extends Civilian
{
    public MunitionsShip()
    {
        //       setPosition(getX() - getWidth()/2, getY() - getHeight()/2);

    }

    public void startingAttributes()
    {
        setHealth(5000);
        setMunitions(500);
        setMunitionsRegeneration(2);

        modules.add(new MunitionsDepot(this));
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

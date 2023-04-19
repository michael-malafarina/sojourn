package com.sojourn.game.entity.unit.civilian;

import com.badlogic.gdx.graphics.Texture;
import com.sojourn.game.Textures;
import com.sojourn.game.entity.component.module.RepairBay;

public class RepairShip extends Civilian
{
    public RepairShip()
    {
        //       setPosition(getX() - getWidth()/2, getY() - getHeight()/2);
        description = "A repair ship";

    }

    public void startingAttributes()
    {
        setHealth(5000);
        setCost(30);

        modules.add(new RepairBay(this));


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

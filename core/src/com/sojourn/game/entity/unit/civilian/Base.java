package com.sojourn.game.entity.unit.civilian;

import com.badlogic.gdx.graphics.Texture;
import com.sojourn.game.Textures;
import com.sojourn.game.entity.component.module.MunitionsDepot;

public class Base extends Civilian
{
    public Base()
    {

    }

    public void startingAttributes()
    {
        setHealth(5000);
        setMunitions(300);
        setMunitionsRegeneration(1);

        modules.add(new MunitionsDepot(this));
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
    public Texture getSpriteSheet() {
        return Textures.base;
    }

}

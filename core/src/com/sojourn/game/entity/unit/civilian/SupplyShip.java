package com.sojourn.game.entity.unit.civilian;

import com.badlogic.gdx.graphics.Texture;
import com.sojourn.game.Sojourn;
import com.sojourn.game.Textures;

public class SupplyShip extends Civilian
{
    public SupplyShip()
    {
        description = "A supply ship";
    }

    public void startingAttributes()
    {
        setHealth(5000);
        setCost(10);
    }

    public void onBuild()
    {
        Sojourn.player.getSupply().increaseMaximum(2);
        Sojourn.player.getSupply().increase(2);
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
        return 5;
    }

    @Override
    public Texture getSpriteSheet() {
        return Textures.boxy;
    }

}

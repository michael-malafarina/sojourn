package com.sojourn.game.entity.unit.ship;

import com.badlogic.gdx.graphics.Texture;
import com.sojourn.game.Textures;
import com.sojourn.game.entity.component.weapon.Autocannon;
import com.sojourn.game.entity.component.weapon.Brightlance;

public class Hero extends Tank
{
    public void startingAttributes()
    {
        setHealth(600);
        setSquadSize(1);
        setSpeed(50);
        setAcceleration(50);
        setCost(70);

        weapons.add(new Brightlance(this));
        weapons.add(new Autocannon(this));
        setMunitions(12);

    }

    public void actionCombat()
    {
        super.actionCombat();

    }

    @Override
    public Texture getSpriteSheet()
    {
        return Textures.pincer;
    }

    @Override
    public int getWidth() {
        return 72;
    }

    @Override
    public int getHeight() {
        return 72;
    }

}

package com.sojourn.game.entity.unit.ship;

import com.badlogic.gdx.graphics.Texture;
import com.sojourn.game.Textures;
import com.sojourn.game.entity.component.weapon.Autocannon;

public class Dreadnought extends Tank
{
    public Dreadnought()
    {
        super();
        description = "A single powerful battleship.";
    }

    public void startingAttributes()
    {
        setHealth(700);
        setSquadSize(1);
        setSpeed(50);
        setAcceleration(50);
        setCost(70);
        setMunitions(20*4);



        weapons.add(new Autocannon(this));
        weapons.add(new Autocannon(this));
        weapons.add(new Autocannon(this));
        weapons.add(new Autocannon(this));

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
        return 96;
    }

    @Override
    public int getHeight() {
        return 96;
    }

}

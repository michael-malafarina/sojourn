package com.sojourn.game.entity.unit.ship;

import com.badlogic.gdx.graphics.Texture;
import com.sojourn.game.Textures;
import com.sojourn.game.entity.component.weapon.Brightlance;

public class Lancer extends Sniper
{
    public Lancer()
    {
        super();
        description = "A long range high damage sniper";
    }

    public void startingAttributes()
    {
        setHealth(70);
        setSquadSize(5);
        setSpeed(25);
        setAcceleration(25);
        setCost(75);

        weapons.add(new Brightlance(this));
    }

    public void actionCombat()
    {
        super.actionCombat();

    }

    @Override
    public Texture getSpriteSheet()
    {
        return Textures.boxy;
    }

    @Override
    public int getWidth() {
        return 32;
    }

    @Override
    public int getHeight() {
        return 32;
    }


}

package com.sojourn.game.entity.unit.ship;

import com.badlogic.gdx.graphics.Texture;
import com.sojourn.game.Textures;
import com.sojourn.game.entity.component.weapon.SmallLaser;

public class Guardian extends Tank
{
    public Guardian()
    {
        super();
        description = "A highly defensive ship.";
    }

    public void startingAttributes()
    {
        setHealth(200);
        setSquadSize(4);
        setSpeed(40);
        setAcceleration(40);
        setCost(65);

        weapons.add(new SmallLaser(this));
    }

    public void actionCombat()
    {
        super.actionCombat();

    }

    @Override
    public Texture getSpriteSheet()
    {
        return Textures.bubble;
    }

    @Override
    public int getWidth() {
        return 48;
    }

    @Override
    public int getHeight() {
        return 48;
    }

}

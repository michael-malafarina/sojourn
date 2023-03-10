package com.sojourn.game.entity.unit.ship;

import com.badlogic.gdx.graphics.Texture;
import com.sojourn.game.Sojourn;
import com.sojourn.game.Textures;
import com.sojourn.game.entity.component.weapon.Autocannon;
import com.sojourn.game.entity.component.weapon.SmallLaser;

public class Raider extends Skirmisher
{
    public void startingAttributes()
    {
        setHealth(100);
        setSquadSize(4);
        setSpeed(60);
        setAcceleration(60);
        setCost(75);

        if(getTeam() == Sojourn.currentEnemy)
        {
            weapons.add(new SmallLaser(this));
        }
        else
        {
            weapons.add(new Autocannon(this));
            setMunitions(16);

        }
    }

    public void actionCombat()
    {
        super.actionCombat();
    }

    @Override
    public Texture getSpriteSheet()
    {
        return Textures.raider;
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

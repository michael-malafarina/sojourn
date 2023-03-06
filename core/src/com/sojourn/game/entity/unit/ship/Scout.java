package com.sojourn.game.entity.unit.ship;

import com.badlogic.gdx.graphics.Texture;
import com.sojourn.game.Textures;
import com.sojourn.game.entity.component.weapon.SmallLaser;

public class Scout extends Ship
{
    public void startingAttributes()
    {
        setHealth(50);
        setSquadSize(6);

        weapons.add(new SmallLaser(this));
    }

    public void actionCombat()
    {
        super.actionCombat();

//        if(getDistance(getNearestEnemyUnit()) < 120)
//        {
//            takeDamage(1, getNearestEnemyUnit());
//        }

    }

    public int getValueBase() {
        return 3;
    }


    @Override
    public Texture getSpriteSheet()
    {
        return Textures.scout;
    }

    @Override
    public int getWidth() {
        return 24;
    }

    @Override
    public int getHeight() {
        return 24;
    }

    @Override
    public float getMaxSpeedBase()
    {
        return 80;
    }

    @Override
    public float getAccelerationBase()
    {
        return 80;
    }
}

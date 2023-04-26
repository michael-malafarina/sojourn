package com.sojourn.game.entity.unit.ship;

import com.badlogic.gdx.graphics.Texture;
import com.sojourn.game.Textures;
import com.sojourn.game.entity.component.weapon.MachineGun;

public class Scout extends Skirmisher
{
    public Scout()
    {
        super();
        description = "A light skirmisher squad";
    }


    public void startingAttributes()
    {
        setHealth(40);
        setSquadSize(8);
        setSpeed(80);
        setAcceleration(80);
        setCost(50);

//        if(getTeam() == Sojourn.currentEnemy)
//        {
//            weapons.add(new SmallLaser(this));
//        }
//        else
//        {
            weapons.add(new MachineGun(this));
            setMunitions(8);
//
//        }

    }

    public void actionCombat()
    {
        super.actionCombat();

//        if(getDistance(getNearestEnemyUnit()) < 120)
//        {
//            takeDamage(1, getNearestEnemyUnit());
//        }

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

}

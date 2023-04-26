package com.sojourn.game.entity.unit.ship;

import com.badlogic.gdx.graphics.Texture;
import com.sojourn.game.Textures;
import com.sojourn.game.entity.component.weapon.WeaponMissile;

public class Raider extends Skirmisher
{
    public Raider()
    {
        super();
        description = "A high damage, close ranged medium unit.";
    }

    public void startingAttributes()
    {

        setHealth(100);
        setSquadSize(4);
        setSpeed(60);
        setAcceleration(60);
        setCost(75);

//        if(getTeam() == Sojourn.currentEnemy)
//        {
//            weapons.add(new SmallLaser(this));
//        }
//        else
//        {
            weapons.add(makeWeapon());
            setMunitions(16);
//        }
    }

    public WeaponMissile makeWeapon()
    {
        WeaponMissile w = new WeaponMissile(this);
        w.setRange(400);
        w.setDamage(12);
        w.setMunitionCost(2);
        w.setUseTimes(20, 40, 60);

        return w;
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

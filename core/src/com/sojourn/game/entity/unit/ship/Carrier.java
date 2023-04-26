package com.sojourn.game.entity.unit.ship;

import com.badlogic.gdx.graphics.Texture;
import com.sojourn.game.Textures;
import com.sojourn.game.entity.component.module.MunitionsDepot;
import com.sojourn.game.entity.component.module.RepairBay;
import com.sojourn.game.entity.component.weapon.SmallLaser;

public class Carrier extends BigShip
{
    public Carrier()
    {
        super();
        description = "An enemy carrier";
    }

    public void startingAttributes()
    {
        setHealth(750);
        setSquadSize(1);
//        setSpeed(50);
//        setAcceleration(50);
        setCost(100);

        modules.add(new RepairBay(this));
        modules.add(new MunitionsDepot(this));


        weapons.add(new SmallLaser(this));

//
//        weapons.add(new Autocannon(this));
//        weapons.add(new Autocannon(this));
//        weapons.add(new Autocannon(this));
//        weapons.add(new Autocannon(this));

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
        return 128;
    }

    @Override
    public int getHeight() {
        return 128;
    }

}

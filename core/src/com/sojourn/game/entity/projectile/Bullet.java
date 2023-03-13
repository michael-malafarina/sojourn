package com.sojourn.game.entity.projectile;

import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.component.weapon.Weapon;

public class Bullet extends Projectile
{

    public Bullet(Entity owner, Entity target, Weapon weapon) {
        super(owner, target, weapon);
    }

    public void setSize(int width, int height)
    {
       this.width = width;
       this.height = height;
    }


    @Override
    public void actionPlanning() {

    }

    @Override
    public void actionCombat()
    {
        moveTo(target);

        if(getCenterPosition().dst(target.getCenterPosition()) < (getWidth() + getHeight()) / 2)
        {
            setExpired();
           weapon.effect(target);
        }

    }

    public float getPercentProgress()
    {
        return ((float) getTimer()) / ((float) weapon.getActivationTime());
    }

}

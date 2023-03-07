package com.sojourn.game.entity.component.weapon;

import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.EntityManager;
import com.sojourn.game.entity.projectile.Bullet;
import com.sojourn.game.entity.unit.Unit;

abstract public class WeaponKinetic extends Weapon
{
    public WeaponKinetic(Unit owner) {
        super(owner);
    }

    abstract public int getBulletWidth();

    abstract public int getBulletHeight();

    public void activationBegin()
    {
        for(Entity e : targets.getTargets())
        {
            Bullet b = new Bullet(owner, e, this);
            b.setSize(getBulletWidth(), getBulletHeight());
            EntityManager.addEntity(b);
        }

    }



    public void activationEnd()
    {

    }

    @Override
    public boolean targetsSelf() {
        return false;
    }
}

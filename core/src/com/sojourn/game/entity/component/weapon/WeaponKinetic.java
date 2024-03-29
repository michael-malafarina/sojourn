package com.sojourn.game.entity.component.weapon;

import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.EntityManager;
import com.sojourn.game.entity.projectile.Bullet;
import com.sojourn.game.entity.unit.Unit;

public class WeaponKinetic extends Weapon
{
    public WeaponKinetic(Unit owner) {
        super(owner);
        size = 8;
    }

    public void activationBegin()
    {
        super.activationBegin();

        for(Entity e : targets.getTargets())
        {
            Bullet b = new Bullet(owner, e, this);
            b.setSize(getProjectileSize(), getProjectileSize());
            EntityManager.addEntity(b);
        }

    }



    public void activationEnd()
    {
        super.activationEnd();
    }

    @Override
    public boolean targetsSelf() {
        return false;
    }
}

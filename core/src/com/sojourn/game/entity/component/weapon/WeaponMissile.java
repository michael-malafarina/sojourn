package com.sojourn.game.entity.component.weapon;

import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.EntityManager;
import com.sojourn.game.entity.projectile.Missile;
import com.sojourn.game.entity.unit.Unit;

public class WeaponMissile extends Weapon
{

    public WeaponMissile(Unit owner) {
        super(owner);
        size = 16;
    }

    public void activationBegin()
    {
        super.activationBegin();

        for(Entity e : targets.getTargets())
        {
            Missile m = new Missile(owner, e, this);
            m.setSize(getProjectileSize());
            EntityManager.addEntity(m);
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

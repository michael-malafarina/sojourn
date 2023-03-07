package com.sojourn.game.entity.component.weapon;

import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.EntityManager;
import com.sojourn.game.entity.projectile.Beam;
import com.sojourn.game.entity.unit.Unit;

import java.util.ArrayList;
import java.util.List;

abstract public class WeaponLaser extends Weapon
{
    List<Beam> beams;

    public WeaponLaser(Unit owner) {
        super(owner);
    }

    abstract public int getAnimationWidth();

    abstract public float getAnimationAlpha();

    abstract public boolean getAnimationBurst();


    public void activationBegin()
    {
        beams = new ArrayList<>();

        for(Entity e : targets.getTargets())
        {
            Beam b = new Beam(owner, e, this);
            b.setWidth(getAnimationWidth());
            b.setAlpha(getAnimationAlpha());
            b.setBurst(getAnimationBurst());

//            if(owner.getTeam() instanceof TeamPlayer)
//            {
//                System.out.println("MAKING A BEAM");
//            }
            beams.add(b);
            EntityManager.addEntity(b);
        }

    }

    public void activationEnd()
    {
        for(Beam b : beams)
        {
            b.end();
        }

        beams.clear();

//        targets.getTargets().forEach(n -> n.takeDamage(15, owner));
    }



    @Override
    public boolean targetsSelf() {
        return false;
    }
}

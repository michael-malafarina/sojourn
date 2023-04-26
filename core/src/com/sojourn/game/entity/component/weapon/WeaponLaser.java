package com.sojourn.game.entity.component.weapon;

import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.EntityManager;
import com.sojourn.game.entity.projectile.Beam;
import com.sojourn.game.entity.unit.Unit;

import java.util.ArrayList;
import java.util.List;

public class WeaponLaser extends Weapon
{
    List<Beam> beams;

    float alpha;
    boolean burst;

    public WeaponLaser(Unit owner) {
        super(owner);
        setMunitionCost(0);
        size = 3;
        burst = false;
        alpha = .7f;
    }

    public float getAnimationAlpha()
    {
        return alpha;
    }

    public boolean getAnimationBurst()
    {
        return burst;
    }


    public void activationBegin()
    {
        super.activationBegin();

        beams = new ArrayList<>();

        for(Entity e : targets.getTargets())
        {
            Beam b = new Beam(owner, e, this);
            b.setWidth(getProjectileSize());
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
        super.activationEnd();

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

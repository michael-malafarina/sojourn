package com.sojourn.game.entity.component.weapon;

import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.EntityManager;
import com.sojourn.game.entity.projectile.Beam;

import java.util.ArrayList;
import java.util.List;

public class Laser extends Weapon
{
    List<Beam> beams;
    /******** Use and Activation  ********/

    public Laser(Entity owner) {
        super(owner);
    }

    public void activationBegin()
    {
        beams = new ArrayList<>();

        for(Entity e : targets.getTargets())
        {
            Beam b = new Beam(owner, e);
            beams.add(b);
            EntityManager.addEntity(b);
        }

        targets.getTargets().forEach(n -> n.takeDamage(5, owner));
    }


    public void activationEnd()
    {
        for(Beam b : beams)
        {
            b.setExpired();
        }
        beams.clear();

        targets.getTargets().forEach(n -> n.takeDamage(5, owner));
    }

    @Override
    public int getPreparationTime() {
        return 60;
    }

    @Override
    public int getActivationTime() {
        return 30;
    }

    @Override
    public int getRecoveryTime() {
        return 120;
    }

    @Override
    public boolean targetsSelf() {
        return false;
    }
}

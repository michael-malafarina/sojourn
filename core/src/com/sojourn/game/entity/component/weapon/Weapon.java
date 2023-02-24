package com.sojourn.game.entity.component.weapon;

// Weapons can have
// Any number of projectiles divided across any number of targets (including self or a point)
// Projectiles may be all at once or staggered
// Try and handle as many of the old Astraeus subclasses in the core as possible

//

import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.component.Component;

abstract public class Weapon extends Component
{
    // Data


    // Constructor

    public Weapon(Entity owner) {
        super(owner);
    }

    public boolean targetsSelf()
    {
        return false;
    }
    public int getNumTargets()
    {
        return 1;
    }


    // Accessors




    public void preparationBegin()
    {
        owner.turnTo(targets.getCenter());
    }



}

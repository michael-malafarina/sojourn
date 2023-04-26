package com.sojourn.game.entity.component.weapon;

// Weapons can have
// Any number of projectiles divided across any number of targets (including self or a point)
// Projectiles may be all at once or staggered
// Try and handle as many of the old Astraeus subclasses in the core as possible

//

import com.sojourn.game.entity.Attribute;
import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.component.Component;
import com.sojourn.game.entity.unit.Unit;

abstract public class Weapon extends Component
{
    final public static float CRIT_CHANCE_BASE = .05f;
    final public static float CRIT_DAMAGE_BASE = 1.5f;
    int size;

    // Data

    Attribute critChance;
    Attribute critDamage;

    // Constructor
    public Weapon(Unit owner)
    {
        super(owner);

        setCritChance(CRIT_CHANCE_BASE);
        setCritDamage(CRIT_DAMAGE_BASE);
    }

    int getProjectileSize()
    {
        return size;
    }

    public void effect(Entity target)
    {
        dealDamage(target);
    }

    public boolean rollHit(Entity target)
    {
        return true;
    }

    public void dealDamage(Entity target)
    {
        float damage = getDamage().getValue();
        boolean isCritical = false;

        double r = Math.random();

        if(r < critChance.getValue())
        {
            damage *= critDamage.getValue();
            isCritical = true;
        }

        target.takeDamage(damage, isCritical, owner);
    }

    public Attribute getCritChance()
    {
        return critChance;
    }

    public Attribute getCritDamage()
    {
        return critDamage;
    }

    protected void setCritChance(float baseValue)
    {
        critChance = new Attribute(owner.getTeam().getTeamBonusManager().getCritChance(), baseValue);
    }

    protected void setCritDamage(float baseValue)
    {
        critDamage = new Attribute(owner.getTeam().getTeamBonusManager().getCritDamage(), baseValue);
    }

    public boolean canUse()
    {
//        if(owner instanceof Dreadnought && super.canUse())
//        {
//            System.out.println(StateGameplay.getTime() + " " + owner + " " +
//                    owner.getWeapons().anyWeaponBeingPrepared());
//        }



        return super.canUse() &&
                !owner.getWeapons().anyWeaponBeingPrepared();
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
        super.preparationBegin();
        owner.turnTo(targets.getCenter());
    }



}

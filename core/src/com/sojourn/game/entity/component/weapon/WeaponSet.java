package com.sojourn.game.entity.component.weapon;

import com.sojourn.game.entity.Entity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

public class WeaponSet
{
    ArrayList<Weapon> weapons;

    public WeaponSet()    {
        weapons = new ArrayList<>();
    }

    public void add(Weapon w)    {
        weapons.add(w);
    }

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    public void update()
    {
        weapons.forEach(Weapon::update);
    }

    public boolean anyWeaponBeingPrepared()
    {
        for(Weapon w : weapons)
        {
            if(w.isPreparing())
            {
                return true;
            }
        }
        return false;
    }

    public float getShortestRange()
    {
        Optional<Weapon> w = weapons.stream().min(Comparator.comparing(Weapon::getMaxRange));

        if(w != null)
        {
            return w.get().getMaxRange();
        }
        return 0;
    }

    public float getLongestRange()
    {
        Optional<Weapon> w = weapons.stream().max(Comparator.comparingInt(Weapon::getMaxRange));

        if(w != null)
        {
            return w.get().getMaxRange();
        }
        return 0;
    }

    public void useAllSingleTargetWeapons(Entity e)
    {
        for(Weapon w : weapons)
        {
            if(w.getNumTargets() == 1 && !w.targetsSelf() && w.canUse(e)) {
                w.use(e);
                return;         // can't fire more than one per frame
            }
        }
    }
}

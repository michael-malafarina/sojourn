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

    public float getShortestRange()
    {
        Optional<Weapon> w = weapons.stream().min(Comparator.comparingInt(Weapon::getRange));

        if(w != null)
        {
            return w.get().getRange();
        }
        return 0;
    }

    public float getLongestRange()
    {
        Optional<Weapon> w = weapons.stream().max(Comparator.comparingInt(Weapon::getRange));

        if(w != null)
        {
            return w.get().getRange();
        }
        return 0;
    }

    public void useAllSingleTargetWeapons(Entity e)
    {
        for(Weapon w : weapons)
        {
            if(w.getNumTargets() == 1 && !w.targetsSelf()) {
                w.use(e);
            }
        }
    }
}

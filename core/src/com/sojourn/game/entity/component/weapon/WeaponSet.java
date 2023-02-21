package com.sojourn.game.entity.component.weapon;

import java.util.ArrayList;

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
}

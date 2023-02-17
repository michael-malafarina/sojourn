package com.sojourn.game.entity;

import com.sojourn.game.entity.unit.ship.Ship;

import java.util.List;

public class ControlGroup
{
    List<Ship> ships;
    int key;

    ControlGroup(List<Ship> ships, int key)
    {
        this.ships = ships;
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public void activate()
    {
        ships.forEach(Entity::clicked);
    }
}

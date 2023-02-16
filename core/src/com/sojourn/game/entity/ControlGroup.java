package com.sojourn.game.entity;

import java.util.List;

public class ControlGroup
{
    List<Unit> units;
    int key;

    ControlGroup(List<Unit> units, int key)
    {
        this.units = units;
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public void activate()
    {
        units.forEach(Entity::clicked);
    }
}

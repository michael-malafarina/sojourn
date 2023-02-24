package com.sojourn.game.faction;

import java.util.ArrayList;
import java.util.List;

public class Wave
{
    List<UnitGroup> groups;
    Team team;

    Wave(Team team)
    {
        groups = new ArrayList<>();
        this.team = team;
    }

    public void addGroup(UnitGroup group)
    {
        groups.add(group);
    }
}

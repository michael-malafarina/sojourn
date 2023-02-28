package com.sojourn.game.faction;

import java.util.ArrayList;
import java.util.List;

public class Wave
{
    List<Squad> groups;
    Team team;

    Wave(Team team)
    {
        groups = new ArrayList<>();
        this.team = team;
    }

    public void addGroup(Squad group)
    {
        groups.add(group);
    }
}

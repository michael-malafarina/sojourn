package com.sojourn.game.entity;

import java.util.ArrayList;
import java.util.List;

public class ControlGroupSet
{
    List<ControlGroup> groups;

    public ControlGroupSet()
    {
        groups = new ArrayList<>();
    }

    public void addGroup(List<Unit> units, int key)
    {
        for(int i = 0; i < groups.size(); i++)
        {
            if(groups.get(i).getKey() == key)
            {
                groups.remove(i);
                i--;
            }
        }
        groups.add(new ControlGroup(units, key));
    }

    public void activate(int key)
    {
        for(ControlGroup g : groups)
        {
            if(key == g.getKey())
            {
                g.activate();
            }
        }
    }
}

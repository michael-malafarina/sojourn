package com.sojourn.game.faction;

import com.badlogic.gdx.math.Vector2;
import com.sojourn.game.entity.EntityManager;
import com.sojourn.game.entity.unit.Unit;

import java.util.ArrayList;
import java.util.List;

public class UnitGroup
{
    List<Unit> units;
    Vector2 position;
    Team team;

    UnitGroup(Team team, Vector2 position)
    {
        units = new ArrayList<>();
        this.team = team;
        this.position = position;
    }

    void add(Unit u)
    {
        units.add(u);
    }

    void add(Class<? extends Unit> clazz)
    {
        Unit u = EntityManager.addUnit(clazz, position, team);
        add(u);
    }

    void add(Class<? extends Unit> clazz, float totalValue)
    {
       // Create a prototype unit
       Unit u = EntityManager.addUnit(clazz, position, team);

       // If you can afford it, add it to the list and "spend" value
       if(totalValue >= u.getValue())
       {
         add(u);
         totalValue -= u.getValue();
       }

        // Keep going as long as you have money left to make more
       if(totalValue >= u.getValue())
       {
           add(clazz, totalValue);
       }


    }


    public void update()
    {
        // Removing expired entities
        for(int i = 0; i < units.size(); i++)
        {
            if(units.get(i).isExpired())
            {
                units.remove(i);
                i--;
            }
        }
    }

    void renderShape()
    {
        // debug messages - draw circles around groups?
    }
}

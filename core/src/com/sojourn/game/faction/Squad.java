package com.sojourn.game.faction;

import com.badlogic.gdx.math.Vector2;
import com.sojourn.game.entity.Attribute;
import com.sojourn.game.entity.EntityManager;
import com.sojourn.game.entity.images.SquadImage;
import com.sojourn.game.entity.unit.Unit;

import java.util.ArrayList;
import java.util.List;

public class Squad
{
    List<Unit> units;

    Vector2 position;
    Team team;

    Attribute aggregateHealth;

    SquadImage image;
    private int maxSize;

    public Squad(Team team, Vector2 position)
    {
        units = new ArrayList<>();
        image = new SquadImage(this);

        this.team = team;
        this.position = position;
        aggregateHealth = new Attribute(0);
    }

    public void add(Unit u)
    {
        units.add(u);

        // For now, max size reflects the largest size.  Later on, we'll make this prescriptive.
        if(units.size() > maxSize)
        {
            maxSize++;
        }
    }

    void add(Class<? extends Unit> clazz)
    {
        Unit u = EntityManager.addUnit(clazz, position, team, this);
        add(u);
    }

    void buildUnits(Class<? extends Unit> clazz, float totalValue)
    {
       // Create a prototype unit
       Unit u = EntityManager.addUnit(clazz, position, team, this);

       // If you can afford it, add it to the list and "spend" value
       if(totalValue >= u.getValue())
       {
         add(u);
         totalValue -= u.getValue();
       }

        // Keep going as long as you have money left to make more
       if(totalValue >= u.getValue())
       {
           buildUnits(clazz, totalValue);
       }
    }

    public Attribute getHealth()
    {
        return aggregateHealth;
    }

    public Team getTeam()
    {
        return team;
    }

    public void update()
    {

        // Update the squad's health to reflect the total of all units' health

        int curHealth = 0;
        int maxHealth = 0;

        for(Unit u : units)
        {
            curHealth += u.getHealth().getCurrent();
        }

        if(!units.isEmpty())
        {
            maxHealth = Math.round(units.get(0).getHealth().getMaximum() * maxSize);
        }

        aggregateHealth = new Attribute(curHealth, maxHealth);

        System.out.println("updating " + getCenter());

        image.update();
    }

    public void removeExpiredUnits()
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

    public Vector2 getCenter()
    {
        Vector2 center = new Vector2();
        for(Unit u : units)
        {
            center.add(u.getCenterPosition());
        }

        center.x /= units.size();
        center.y /= units.size();

        return center;
    }

    public void render()
    {
        System.out.println("rendering");
        image.render();
    }

    public void renderShape()
    {
        image.renderShape();

    }
}

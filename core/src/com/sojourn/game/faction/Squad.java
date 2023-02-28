package com.sojourn.game.faction;

import com.badlogic.gdx.math.Vector2;
import com.sojourn.game.entity.Attribute;
import com.sojourn.game.entity.EntityManager;
import com.sojourn.game.entity.images.SquadImage;
import com.sojourn.game.entity.unit.Unit;
import com.sojourn.game.entity.unit.ship.Ship;

import java.util.ArrayList;
import java.util.List;

public class Squad
{
    List<Ship> units;

    Vector2 position;
    private Vector2 anchor;

    Team team;

    Attribute aggregateHealth;

    private Vector2 destination;


    SquadImage image;
    private int maxSize;
    boolean selected = true;

    public Squad(Team team, Vector2 position)
    {
        units = new ArrayList<>();
        image = new SquadImage(this);

        this.team = team;
        this.position = position;
        aggregateHealth = new Attribute(0);
        setDestination(position);
    }

    public void add(Ship u)
    {
        units.add(u);

        // For now, max size reflects the largest size.  Later on, we'll make this prescriptive.
        if(units.size() > maxSize)
        {
            maxSize++;
        }
    }

    public Vector2 getAnchor()
    {
        return anchor;
    }

    public Vector2 getDestination()
    {
        return destination;
    }

    public boolean hasDestination()
    {
        return destination != null;
    }

    public void setDestination(Vector2 destination)
    {
        this.destination = destination;
        anchor = destination;

    }

    public void setDestination(float x, float y)
    {
        setDestination(new Vector2(x, y));

    }

    void add(Class<? extends Ship> clazz)
    {
        Ship ship = (Ship) EntityManager.addUnit(clazz, position, team, this);
        add(ship);
    }

    void buildUnits(Class<? extends Ship> clazz, float totalValue)
    {
       // Create a prototype unit
        Ship ship = (Ship) EntityManager.addUnit(clazz, position, team, this);

       // If you can afford it, add it to the list and "spend" value
       if(totalValue >= ship.getValue())
       {
         add(ship);
         totalValue -= ship.getValue();
       }

        // Keep going as long as you have money left to make more
       if(totalValue >= ship.getValue())
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

    public void clicked()
    {
        selected = true;
        for(Unit u : units)
        {
            u.select();
        }
    }

    public void select()
    {
        selected = true;
    }

    public void unselect()
    {
        selected = false;
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
        image.render();
    }

    public void renderShape()
    {
        image.renderShape();

    }
}

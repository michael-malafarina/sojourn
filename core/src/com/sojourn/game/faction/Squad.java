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
    Class<? extends Ship> type;

    public Squad(Class<? extends Ship> type, Team team, Vector2 position, int maxSize)
    {
        this(type, team, position);
        this.maxSize = maxSize;
    }

    public Squad(Class<? extends Ship> type, Team team, Vector2 position)
    {
        units = new ArrayList<>();
        image = new SquadImage(this);

        this.team = team;
        this.position = position;
        this.maxSize = maxSize;
        this.type = type;
        aggregateHealth = new Attribute(0);
        setDestination(position);
    }

    public int getMaxSize()
    {
        return maxSize;
    }

    public int getSize()
    {
        return units.size();
    }

    public int countMissingUnits()  { return getMaxSize() - units.size();}

    public boolean isFull()
    {
        return getSize() == getMaxSize();
    }

    public void add(Ship u)
    {
        units.add(u);
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
        Ship ship = (Ship) EntityManager.addEntity(clazz, position, team, this);
        add(ship);
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


    public void restore()
    {
        int spawnAmount = countMissingUnits();

        for(int i = 0; i < spawnAmount; i++)
        {
           EntityManager.addEntity(type, team.getSpawnPoint(), team, this);
        }

//        units.forEach(a -> a.restoreHealth());
    }
}

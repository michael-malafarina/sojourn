package com.sojourn.game.faction;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.sojourn.game.display.Shape;
import com.sojourn.game.entity.EntityManager;
import com.sojourn.game.entity.unit.Unit;

import java.util.ArrayList;
import java.util.List;

public class Squad
{
    List<Unit> units;
    Vector2 position;
    Team team;

    public Squad(Team team, Vector2 position)
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

    }

    public void renderShape()
    {
        Shape.getRenderer().set(ShapeRenderer.ShapeType.Filled);
        Shape.getRenderer().setColor(team.getFaction().getColor(0));
        Shape.getRenderer().ellipse(getCenter().x, getCenter().y, 20, 20);
        Shape.getRenderer().set(ShapeRenderer.ShapeType.Line);
        Shape.getRenderer().setColor(team.getFaction().getColor(1));
        Shape.getRenderer().ellipse(getCenter().x, getCenter().y, 20, 20);
    }
}

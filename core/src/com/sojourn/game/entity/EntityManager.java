package com.sojourn.game.entity;

import com.sojourn.game.Utility;
import com.sojourn.game.display.Display;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class EntityManager
{
    private static ArrayList<Entity> entities;

    public EntityManager()
    {
        newGame();
    }

    public void newGame()
    {
        entities = new ArrayList<>();

        for(int i = 0; i < 7; i++)
        {
            addUnit(Scout.class);
            addUnit(Raider.class);
        }
    }

    public static ArrayList<Entity> getEntities()
    {
        return entities;
    }


    public static ArrayList<Unit> getUnits()
    {
        ArrayList<Unit> units = new ArrayList<>();
        for(Entity e : entities)
        {
            if(e instanceof Unit)
            {
                units.add((Unit)e);
            }
        }
        return units;
    }

    public void update(float delta)
    {
        entities.forEach((n)->n.update(delta));

    }

    public void addUnit(Class<? extends Unit> clazz)
    {
        Unit u = unitFactory(clazz);
        u.setPosition(Utility.random(Display.WIDTH), Utility.random(Display.HEIGHT));
        entities.add(u);
    }

    public Unit unitFactory(Object o)
    {
        Class<? extends Unit> clazz = (Class<? extends Unit>) o;

        Unit u = null;

        try
        {
            u = clazz.getDeclaredConstructor().newInstance();
        }
        catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e)
        {
            e.printStackTrace();
        }

        return u;
    }

}

package com.sojourn.game;

import com.sojourn.game.display.Display;
import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.Raider;
import com.sojourn.game.entity.Scout;
import com.sojourn.game.entity.Unit;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class EntityManager
{
    private ArrayList<Entity> entities;


    EntityManager()
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

    public  ArrayList<Entity> getEntities()
    {
        return entities;
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

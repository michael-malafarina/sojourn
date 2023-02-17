package com.sojourn.game.entity;

import com.badlogic.gdx.math.Vector2;
import com.sojourn.game.Sojourn;
import com.sojourn.game.Utility;
import com.sojourn.game.display.Display;
import com.sojourn.game.entity.unit.ship.Raider;
import com.sojourn.game.entity.unit.ship.Scout;
import com.sojourn.game.entity.unit.ship.Ship;
import com.sojourn.game.state.faction.Team;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class EntityManager
{
    private static List<Entity> entities;
    private static List<Ship> ships;

    public EntityManager()
    {
        newGame();
    }

    public void newGame()
    {
        entities = new ArrayList<>();
        ships = new ArrayList<>();

        for(int i = 0; i < 4; i++)
        {
            Vector2 pos = new Vector2(Utility.random(Display.WIDTH/2), Utility.random(Display.HEIGHT));
            addUnit(Scout.class, pos, Sojourn.player);
            pos = new Vector2(Utility.random(Display.WIDTH/2), Utility.random(Display.HEIGHT));
            addUnit(Raider.class, pos, Sojourn.player);

            pos = new Vector2(Utility.random(Display.WIDTH/2, Display.WIDTH), Utility.random(Display.HEIGHT));
            addUnit(Scout.class, pos, Sojourn.currentEnemy);
            pos = new Vector2(Utility.random(Display.WIDTH/2, Display.WIDTH), Utility.random(Display.HEIGHT));
            addUnit(Raider.class, pos, Sojourn.currentEnemy);
        }

        updateUnits();

    }

    public static List<Entity> getEntities()    {
        return entities;
    }

    public static List<Ship> getUnits()    {
        return ships;
    }

    private static void updateUnits()
    {
        // Filter out units from the list of entities
        List<Entity> tempUnits = entities.stream().filter(e -> e instanceof Ship).toList();

        // Cast the entities to a list of units
        ships = tempUnits.stream().map(e-> (Ship) e).toList();
    }

    public static List<Ship> getEnemyUnits(Team team)
    {
        return getUnits().stream().filter(u -> u.getTeam().isHostile(team)).toList();
    }

    public void update(boolean planning, float delta)
    {
        // Update specific lists
        updateUnits();

        entities.forEach((n)->n.update(planning, delta));

    }

    public void addUnit(Class<? extends Ship> clazz, Vector2 position, Team team)
    {
        Ship u = unitFactory(clazz);
        u.setPosition(position);
        u.setTeam(team);
        u.setImage();
        entities.add(u);
    }

    public Ship unitFactory(Object o)
    {
        Class<? extends Ship> clazz = (Class<? extends Ship>) o;

        Ship u = null;

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

    // make this better with filters?
    public static Entity getNearestEntity(Entity origin, List<Entity> entitySet)
    {
        float nearestDistance = Float.MAX_VALUE;
        Entity nearestEntity = null;

        for(Entity e : entitySet)
        {
            if(origin != e && origin.getDistance(e) < nearestDistance)
            {
                nearestEntity = e;
                nearestDistance = origin.getDistance(e);
            }
        }

        return nearestEntity;
    }

    // Concern:  Copying the Arraylist makes this run in 2n time rather than n time.
    // The naieve version where I largely rewrite getNearest is faster by 2x
    public static Ship getNearestShip(Ship origin)
    {
        return (Ship) getNearestEntity(origin, new ArrayList<>(getUnits()));
    }

    public static Ship getNearestEnemyShip(Ship origin)
    {
        return (Ship) getNearestEntity(origin, new ArrayList<>(getEnemyUnits(origin.getTeam())));
    }


}

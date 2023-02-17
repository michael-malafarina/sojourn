package com.sojourn.game.entity;

import com.badlogic.gdx.math.Vector2;
import com.sojourn.game.Sojourn;
import com.sojourn.game.Utility;
import com.sojourn.game.display.Display;
import com.sojourn.game.entity.unit.Unit;
import com.sojourn.game.entity.unit.civilian.Carrier;
import com.sojourn.game.entity.unit.civilian.Civilian;
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
    private static List<Unit> units;
    private static List<Ship> ships;
    private static List<Civilian> civilians;

    public EntityManager()
    {
        newGame();
    }

    public void newGame()
    {
        entities = new ArrayList<>();
//        units = new ArrayList<>();
//        ships = new ArrayList<>();

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

        addUnit(Carrier.class, new Vector2(200, 200), Sojourn.player);

        updateUnits();

    }

    public static List<Entity> getEntities()    {
        return entities;
    }

    public static List<Unit> getUnits()    {
        return units;
    }

    public static List<Ship> getShips()    {
        return ships;
    }

    public static List<Civilian> getCivilians()    {
        return civilians;
    }

    private static void updateUnits()
    {
        // Filter out units from the list of entities
        List<Entity> tempUnits = entities.stream().filter(e -> e instanceof Unit).toList();
        List<Entity> tempShips = tempUnits.stream().filter(e -> e instanceof Ship).toList();
        List<Entity> tempCivs = tempUnits.stream().filter(e -> e instanceof Civilian).toList();

        // Cast the entities to a list of units
        units = tempUnits.stream().map(e-> (Unit) e).toList();
        ships = tempShips.stream().map(e-> (Ship) e).toList();
        civilians = tempCivs.stream().map(e-> (Civilian) e).toList();

    }

    public static List<Unit> getEnemyUnits(Team team)
    {
        return getUnits().stream().filter(u -> u.getTeam().isHostile(team)).toList();
    }

    public void update(boolean planning, float delta)
    {
        // Update specific lists
        updateUnits();

        entities.forEach((n)->n.update(planning, delta));

    }

    public void addUnit(Class<? extends Unit> clazz, Vector2 position, Team team)
    {
        Unit u = unitFactory(clazz);
        u.setPosition(position);
        u.setTeam(team);
        u.setImage();
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
    public static Unit getNearestUnit(Entity origin)
    {
        return (Unit) getNearestEntity(origin, new ArrayList<>(getUnits()));
    }

    public static Unit getNearestEnemyShip(Entity origin)
    {
        return (Unit) getNearestEntity(origin, new ArrayList<>(getEnemyUnits(origin.getTeam())));
    }



}

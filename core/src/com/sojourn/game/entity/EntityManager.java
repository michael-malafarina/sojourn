package com.sojourn.game.entity;

import com.badlogic.gdx.math.Vector2;
import com.sojourn.game.Sojourn;
import com.sojourn.game.entity.projectile.Projectile;
import com.sojourn.game.entity.unit.Unit;
import com.sojourn.game.entity.unit.civilian.Base;
import com.sojourn.game.entity.unit.civilian.Civilian;
import com.sojourn.game.entity.unit.ship.Raider;
import com.sojourn.game.entity.unit.ship.Scout;
import com.sojourn.game.entity.unit.ship.Ship;
import com.sojourn.game.faction.Squad;
import com.sojourn.game.faction.Team;
import com.sojourn.game.faction.TeamEnemy;
import com.sojourn.game.faction.TeamPlayer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class EntityManager
{
    private static List<Entity> entities;
    private static List<Unit> units;
    private static List<Ship> ships;
    private static List<Civilian> civilians;
    private static List<Projectile> projectiles;
    private static List<Entity> newEntities;
    private static List<Squad> squads;

    public EntityManager()
    {
        newGame();
    }

    public void newGame()
    {
        entities = new ArrayList<>();
        newEntities = new ArrayList<>();
        squads = new ArrayList<>();
//        units = new ArrayList<>();
//        ships = new ArrayList<>();



        // Human Player
        Team human = Sojourn.player;


        Squad one = new Squad(human,human.getHomePoint() );

        addUnit(Base.class, new Vector2(human.getHomePoint().x, human.getHomePoint().y), human, one);

        for(int j = 0; j < 2; j++)
        {
            Vector2 point = human.getSpawnPoint();
            Squad s = new Squad(human, point);
            for (int i = 0; i < 6; i++)
            {

                addUnit(Scout.class, point, human, s);

            }

            point = human.getSpawnPoint();
            Squad b = new Squad(human, point);

            for (int i = 0; i < 4; i++) {
                addUnit(Raider.class, point, human, b);

            }
        }

        // Computer Player (Hostile)



    }

    public static void spawnEnemyWave()
    {
        TeamEnemy cpu = (TeamEnemy) Sojourn.currentEnemy;
//        addUnit(Carrier.class, new Vector2(cpu.getHomePoint().x, cpu.getHomePoint().y), cpu);

        cpu.getWave(60);

//        for(int i = 0; i < 4; i++)
//        {
//            addUnit(Scout.class, cpu.getSpawnPoint(), cpu);
//            addUnit(Raider.class, cpu.getSpawnPoint(), cpu);
//        }

        updateUnitLists();

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

    public static List<Squad> getSquads()   { return squads;        }

    public static List<Civilian> getCivilians()    {
        return civilians;
    }

    public static List<Projectile> getProjectiles()    {
        return projectiles;
    }
    private static void updateUnitLists()
    {
        // Filter out units from the list of entities
        List<Entity> tempUnits = entities.stream().filter(e -> e instanceof Unit).toList();
        List<Entity> tempShips = tempUnits.stream().filter(e -> e instanceof Ship).toList();
        List<Entity> tempCivs = tempUnits.stream().filter(e -> e instanceof Civilian).toList();
        List<Entity> tempProj = entities.stream().filter(e -> e instanceof Projectile).toList();

        // Cast the entities to a list of units
        units = tempUnits.stream().map(e-> (Unit) e).toList();
        ships = tempShips.stream().map(e-> (Ship) e).toList();
        civilians = tempCivs.stream().map(e-> (Civilian) e).toList();
        projectiles = tempProj.stream().map(e-> (Projectile) e).toList();

    }

    public static List<Unit> getEnemyUnits(Team team)
    {
        return getUnits().stream().filter(u -> u.getTeam().isHostile(team)).toList();
    }

    public static List<Ship> getEnemyShips(Team team)
    {
        return getShips().stream().filter(u -> u.getTeam().isHostile(team)).toList();
    }

    public static List<Ship> getPlayerShips()    {
        return getShips().stream().filter(u -> u.getTeam() instanceof TeamPlayer).toList();
    }

    public void update(boolean planning, float delta)
    {
        // Add new entities created this frame
        entities.addAll(newEntities);
        newEntities.clear();

        // Remove expired units from each unit group
        squads.forEach(a -> a.removeExpiredUnits());

        // Removing expired entities from master list
        for(int i = 0; i < entities.size(); i++)
        {
            if(entities.get(i).isExpired())
            {
                entities.remove(i);
                i--;
            }
        }

        // Update specific unit lists
        updateUnitLists();

        // Tell each entity to update itself
        entities.forEach((n)->n.update(planning, delta));

    }


    public static void addEntity(Entity e)
    {
        newEntities.add(e);
    }

    public static Unit addUnit(Class<? extends Unit> clazz, Vector2 position, Team team, Squad squad)
    {
        Unit u = unitFactory(clazz);
        u.setPosition(position.x - u.getWidth()/2, position.y - u.getHeight()/2);
        u.setTeam(team);
        u.setGroup(squad);
        squad.add(u);
        u.setImage();
        entities.add(u);

        // Add the group to our list of groups if it has not been added yet
        System.out.println(squad);

        if(!squads.contains(squad))
        {
            System.out.println("added");
            squads.add(squad);
        }
        else
        {
            System.out.println("already have it");

        }

        return u;
    }

    public static Unit unitFactory(Object o)
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

    public static Ship getNearestShip(Entity origin)
    {
        return (Ship) getNearestEntity(origin, new ArrayList<>(getShips()));
    }

    public static Unit getNearestEnemyUnit(Entity origin)
    {
        return (Unit) getNearestEntity(origin, new ArrayList<>(getEnemyUnits(origin.getTeam())));
    }

    public static Ship getNearestEnemyShip(Entity origin)
    {
        return (Ship) getNearestEntity(origin, new ArrayList<>(getEnemyShips(origin.getTeam())));
    }




}

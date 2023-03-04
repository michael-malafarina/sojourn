package com.sojourn.game.entity;

import com.badlogic.gdx.math.Vector2;
import com.sojourn.game.Sojourn;
import com.sojourn.game.entity.ambient.EnemyAlert;
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

public class EntityManager {
    private static List<Entity> entities;
    private static List<Unit> units;
    private static List<Ship> ships;
    private static List<Civilian> civilians;
    private static List<Projectile> projectiles;
    private static List<Entity> newEntities;
    private static List<Squad> squads;

    public EntityManager() {
        newGame();
    }

    public void newGame() {
        entities = new ArrayList<>();
        newEntities = new ArrayList<>();
        squads = new ArrayList<>();

        // Human Player

        Team human = Sojourn.player;

        addEntity(Base.class, new Vector2(human.getHomePoint().x, human.getHomePoint().y), human, null);

        spawnPlayerUnits();

    }

    public static void spawnPlayerUnits()
    {
        Team human = Sojourn.player;

        addSquad(Scout.class, human.getSpawnPoint(), human);
        addSquad(Scout.class, human.getSpawnPoint(), human);
        addSquad(Raider.class, human.getSpawnPoint(), human);
        addSquad(Raider.class, human.getSpawnPoint(), human);
    }

    public static List<com.sojourn.game.entity.Entity> getEntities() {
        return entities;
    }

    public static List<Unit> getUnits() {
        return units;
    }

    public static List<Ship> getShips() {
        return ships;
    }

    public static List<Squad> getSquads() {
        return squads;
    }

    public static List<Civilian> getCivilians() {
        return civilians;
    }

    public static List<Projectile> getProjectiles() {
        return projectiles;
    }

    private static void updateUnitLists() {
        // Filter out units from the list of entities
        List<Entity> tempUnits = entities.stream().filter(e -> e instanceof Unit).toList();
        List<Entity> tempShips = tempUnits.stream().filter(e -> e instanceof Ship).toList();
        List<Entity> tempCivs = tempUnits.stream().filter(e -> e instanceof Civilian).toList();
        List<Entity> tempProj = entities.stream().filter(e -> e instanceof Projectile).toList();

        // Cast the entities to a list of units
        units = tempUnits.stream().map(e -> (Unit) e).toList();
        ships = tempShips.stream().map(e -> (Ship) e).toList();
        civilians = tempCivs.stream().map(e -> (Civilian) e).toList();
        projectiles = tempProj.stream().map(e -> (Projectile) e).toList();

    }

    public static List<Unit> getHostileUnits(Team team) {
        return getUnits().stream().filter(u -> u.getTeam().isHostile(team)).toList();
    }

    public static List<Ship> getHostileShips(Team team) {
        return getShips().stream().filter(u -> u.getTeam().isHostile(team)).toList();
    }

    public static List<Ship> getPlayerShips() {
        return getShips().stream().filter(u -> u.getTeam() instanceof TeamPlayer).toList();
    }

    public static List<Squad> getPlayerSquads() {
        return getSquads().stream().filter(u -> u.getTeam() instanceof TeamPlayer).toList();
    }


    public static List<Ship> getEnemyShips() {
        return getShips().stream().filter(u -> u.getTeam() instanceof TeamEnemy).toList();
    }

    public static List<EnemyAlert> getEnemyAlerts()
    {
        List<Entity> tmp = getEntities().stream().filter(e -> e instanceof EnemyAlert).toList();
        return tmp.stream().map(e -> (EnemyAlert) e).toList();
    }

    public void update(boolean planning, float delta) {
        // Add new entities created this frame
        entities.addAll(newEntities);
        newEntities.clear();

        // Remove expired units from each unit group
        squads.forEach(a -> a.removeExpiredUnits());

        // Removing expired entities from master list
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).isExpired()) {
                entities.remove(i);
                i--;
            }
        }

        // Update specific unit lists
        updateUnitLists();

        // Tell each entity to update itself
        entities.forEach((n) -> n.update(planning, delta));

    }


    public static void addEntity(com.sojourn.game.entity.Entity e) {
        newEntities.add(e);
    }

    public static Squad addSquad(Class<? extends Ship> clazz, Vector2 position, Team team)
    {
        Ship prototype = (Ship) EntityManager.entityFactory(clazz);
        int count = prototype.getSquadSizeBase();

        Squad s = new Squad(clazz, team, position, count);

        for (int i = 0; i < count; i++)
        {
            addEntity(clazz, position, team, s);
        }

        updateUnitLists();

        return s;

    }

    public static Entity addEntity(Class<? extends Entity> clazz, Vector2 position, Team team, Squad squad)
    {
        Entity u = entityFactory(clazz);
        u.setPosition(position.x - u.getWidth()/2, position.y - u.getHeight()/2);
        u.setTeam(team);
        u.setAttributes();
//        u.setImage();

        if(squad != null)
        {
            // Add the squad to our list of squads if it has not been added yet
            if(!squads.contains(squad))
            {
                squads.add(squad);
            }

            if(u instanceof Ship)
            {
                ((Ship)u).setGroup(squad);
                squad.add((Ship)u);
            }
        }

        entities.add(u);

        return u;
    }

    public static Entity entityFactory(Object o)
    {
        Class<? extends Entity> clazz = (Class<? extends Entity>) o;

        Entity u = null;

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
        com.sojourn.game.entity.Entity nearestEntity = null;

        for(com.sojourn.game.entity.Entity e : entitySet)
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
        return (Unit) getNearestEntity(origin, new ArrayList<>(getHostileUnits(origin.getTeam())));
    }

    public static Ship getNearestEnemyShip(Entity origin)
    {
        return (Ship) getNearestEntity(origin, new ArrayList<>(getHostileShips(origin.getTeam())));
    }




}

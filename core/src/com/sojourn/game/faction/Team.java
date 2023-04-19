package com.sojourn.game.faction;

import com.badlogic.gdx.math.Vector2;
import com.sojourn.game.entity.AttributePool;
import com.sojourn.game.entity.EntityManager;
import com.sojourn.game.entity.unit.ship.Ship;

abstract public class Team
{
    // Data
    private Faction faction;
    private Vector2 homePoint;
    private TeamBonusManager teamBonusManager;

    final public static int ID_NEUTRAL = 0;
    final public static int ID_PLAYER = 1;
    final public static int ID_ENEMY = 2;
    final public static int ID_HOSTILE = 3;

    private AttributePool supply;

    // Abstract

    abstract public int getTeamID();

    // Constructor

    public Team(Faction faction)
    {
        this.faction = faction;
        teamBonusManager = new TeamBonusManager();
        supply = new AttributePool(teamBonusManager.getSupply(), 10);
    }


    // Accessors

    public TeamBonusManager getTeamBonusManager() { return teamBonusManager; }

    public Faction getFaction()
    {
        return faction;
    }

    public String getColorCode()
    {
        return "[#" + getFaction().getColor(0).toString() + "]";
    }

    public Vector2 getHomePoint()
    {
        return homePoint;
    }

    // this is the same as home point for now, but may have different spawn locations eventually.  keep in case.
    public Vector2 getSpawnPoint()
    {
        return homePoint;
    }

    public Vector2 getSpawnDestination()
    {
        return getSpawnPoint();
    }

    public void setFaction(Faction faction)
    {
        this.faction = faction;
    }

    public void setHomePoint(Vector2 homePoint)
    {
        this.homePoint = homePoint;
    }

    public AttributePool getSupply()
    {
        return supply;
    }

    public boolean isFriendly(Team other)
    {
        if(getTeamID() == 0 || other.getTeamID() == 0)
        {
            return false;
        }

        else if(getTeamID() == other.getTeamID())
        {
            return true;
        }

        return false;
    }

    public boolean isHostile(Team other)
    {
         if(getTeamID() == 0 || other.getTeamID() == 0)
        {
            return false;
        }

        else if(getTeamID() == other.getTeamID())
        {
            return false;
        }

        return true;
    }

    /****** Mutators *********/

    public void render()
    {
        teamBonusManager.render();
    }

    public Squad createSquad(Class<? extends Ship> clazz, Vector2 position)
    {
        return EntityManager.addSquad(clazz, position, this);
    }

    abstract public float getTotalWorth();

    public void setSupply(AttributePool supply)
    {
        this.supply = supply;
    }

}

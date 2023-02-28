package com.sojourn.game.faction;

import com.badlogic.gdx.math.Vector2;
import com.sojourn.game.Utility;

abstract public class Team
{
    // Data
    private Faction faction;
    private Vector2 homePoint;

    final public static int ID_NEUTRAL = 0;
    final public static int ID_PLAYER = 1;
    final public static int ID_ENEMY = 2;
    final public static int ID_HOSTILE = 3;

    final private int SPAWN_X_WIDTH = 800;
    final private int SPAWN_Y_HEIGHT = 400;

    // Abstract

    abstract public int getTeamID();

    // Constructor

    public Team(Faction faction)
    {
        this.faction = faction;
    }


    // Accessors

    public Faction getFaction()
    {
        return faction;
    }

    public Vector2 getHomePoint()
    {
        return homePoint;
    }

    public Vector2 getSpawnPoint()
    {
        return new Vector2(homePoint.x + Utility.random(-SPAWN_X_WIDTH, SPAWN_X_WIDTH), homePoint.y + Utility.random(-SPAWN_Y_HEIGHT, SPAWN_Y_HEIGHT));
    }

    public void setFaction(Faction faction)
    {
        this.faction = faction;
    }

    public void setHomePoint(Vector2 homePoint)
    {
        this.homePoint = homePoint;
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



}

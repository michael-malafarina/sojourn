package com.sojourn.game.faction;

import com.badlogic.gdx.math.Vector2;

abstract public class Team
{
    // Data
    private Faction faction;
    private Vector2 homePoint;

    final public static int ID_NEUTRAL = 0;
    final public static int ID_PLAYER = 1;
    final public static int ID_ENEMY = 2;
    final public static int ID_HOSTILE = 3;

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
